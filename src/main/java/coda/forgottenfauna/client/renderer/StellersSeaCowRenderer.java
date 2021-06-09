package coda.forgottenfauna.client.renderer;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.model.StellersSeaCowModel;
import coda.forgottenfauna.entities.StellersSeaCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StellersSeaCowRenderer extends MobRenderer<StellersSeaCowEntity, StellersSeaCowModel<StellersSeaCowEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgottenFauna.MOD_ID, "textures/entity/stellers_sea_cow.png");

    public StellersSeaCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new StellersSeaCowModel<>(), 1.6F);
    }

    public ResourceLocation getTextureLocation(StellersSeaCowEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(StellersSeaCowEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.scale(entitylivingbaseIn, matrixStackIn, partialTickTime);
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevTilt, entitylivingbaseIn.tilt)));
    }
}
