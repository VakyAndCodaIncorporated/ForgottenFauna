package coda.forgottenfauna.client.renderer;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.model.BaijiModel;
import coda.forgottenfauna.client.model.DodoModel;
import coda.forgottenfauna.entities.BaijiEntity;
import coda.forgottenfauna.entities.DodoEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DodoRenderer extends MobRenderer<DodoEntity, DodoModel<DodoEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgottenFauna.MOD_ID, "textures/entity/dodo.png");

    public DodoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DodoModel<>(), 0.5F);
    }

    public ResourceLocation getEntityTexture(DodoEntity entity) {
        return TEXTURE;
    }

    protected float handleRotationFloat(DodoEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.5F) * f1;
    }
}
