package coda.forgottenfauna.client.renderer;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.model.BaijiModel;
import coda.forgottenfauna.entities.BaijiEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BaijiRenderer extends MobRenderer<BaijiEntity, BaijiModel<BaijiEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgottenFauna.MOD_ID, "textures/entity/baiji.png");

    public BaijiRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BaijiModel<>(), 0.4F);
    }

    public ResourceLocation getEntityTexture(BaijiEntity entity) {
        return TEXTURE;
    }
}
