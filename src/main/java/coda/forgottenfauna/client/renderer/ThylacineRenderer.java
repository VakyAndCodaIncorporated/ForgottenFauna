package coda.forgottenfauna.client.renderer;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.model.ThylacineModel;
import coda.forgottenfauna.entities.ThylacineEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThylacineRenderer extends MobRenderer<ThylacineEntity, ThylacineModel<ThylacineEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgottenFauna.MOD_ID, "textures/entity/thylacine.png");

    public ThylacineRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ThylacineModel<>(), 0.4F);
    }

    public ResourceLocation getEntityTexture(ThylacineEntity entity) {
        return TEXTURE;
    }
}
