package coda.forgottenfauna.client.renderer;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.model.BaijiModel;
import coda.forgottenfauna.client.model.GreatAukModel;
import coda.forgottenfauna.common.entities.BaijiEntity;
import coda.forgottenfauna.common.entities.GreatAukEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GreatAukRenderer extends MobRenderer<GreatAukEntity, GreatAukModel<GreatAukEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgottenFauna.MOD_ID, "textures/entity/great_auk.png");

    public GreatAukRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GreatAukModel<>(), 0.4F);
    }

    public ResourceLocation getTextureLocation(GreatAukEntity entity) {
        return TEXTURE;
    }
}
