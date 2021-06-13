package coda.forgottenfauna.client.model;

import coda.forgottenfauna.common.entities.DodoEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class DodoModel<T extends Entity> extends AgeableModel<DodoEntity> {
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer tail;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer head;
    public ModelRenderer beak;
    public ModelRenderer beakTip;
    public ModelRenderer footRight;
    public ModelRenderer footLeft;
    private float neckXRot;

    public DodoModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.legRight = new ModelRenderer(this, 54, 28);
        this.legRight.setPos(-1.0F, 4.0F, 1.0F);
        this.legRight.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legRight, 0.2617993877991494F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 44);
        this.body.setPos(0.0F, 14.5F, 0.0F);
        this.body.addBox(-5.0F, -4.5F, -6.5F, 10.0F, 9.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.2617993877991494F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 24, 32);
        this.head.setPos(0.0F, -6.5F, -2.3F);
        this.head.addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 23, 21);
        this.beak.setPos(0.0F, 1.0F, -2.5F);
        this.beak.addBox(-1.5F, -2.0F, -4.0F, 3.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 54, 28);
        this.legLeft.mirror = true;
        this.legLeft.setPos(1.0F, 4.0F, 1.0F);
        this.legLeft.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legLeft, 0.2617993877991494F, 0.0F, 0.0F);
        this.footRight = new ModelRenderer(this, 36, 29);
        this.footRight.setPos(0.0F, 5.0F, 1.0F);
        this.footRight.addBox(-4.0F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.footLeft = new ModelRenderer(this, 36, 29);
        this.footLeft.mirror = true;
        this.footLeft.setPos(0.0F, 5.0F, 1.0F);
        this.footLeft.addBox(-1.0F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.wingLeft = new ModelRenderer(this, 43, 14);
        this.wingLeft.mirror = true;
        this.wingLeft.setPos(5.0F, -2.0F, -3.0F);
        this.wingLeft.addBox(0.0F, -1.5F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.beakTip = new ModelRenderer(this, 0, 13);
        this.beakTip.setPos(0.0F, 0.5F, -3.5F);
        this.beakTip.addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(beakTip, -0.3530800996889696F, 0.0F, 0.0F);
        this.wingRight = new ModelRenderer(this, 43, 14);
        this.wingRight.setPos(-5.0F, -2.0F, -3.0F);
        this.wingRight.addBox(-1.0F, -1.5F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 25);
        this.neck.setPos(0.0F, -1.0F, -5.0F);
        this.neck.addBox(-2.5F, -10.0F, -5.0F, 5.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 39, 39);
        this.tail.setPos(0.0F, -2.0F, 3.5F);
        this.tail.addBox(-2.0F, -5.0F, -1.0F, 4.0F, 6.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.2617993877991494F, 0.0F, 0.0F);
        this.body.addChild(this.legRight);
        this.neck.addChild(this.head);
        this.head.addChild(this.beak);
        this.body.addChild(this.legLeft);
        this.legRight.addChild(this.footRight);
        this.legLeft.addChild(this.footLeft);
        this.body.addChild(this.wingLeft);
        this.beak.addChild(this.beakTip);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.neck);
        this.body.addChild(this.tail);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(DodoEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.neck.y = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount - 1.0F;
        this.beakTip.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 0.485F;
        this.beakTip.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount - 0.35F;
        this.body.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.2F;
        this.body.y = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 14.525F;
        this.neck.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        this.tail.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        this.tail.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount - 2.0F;
        this.legRight.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 1.4F * limbSwingAmount + 0.2F;
        this.legRight.y = MathHelper.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 3.96F;
        this.legRight.z = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 1.0F;
        this.footRight.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 4.96F;
        this.legLeft.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.4F * limbSwingAmount + 0.2F;
        this.legLeft.y = MathHelper.cos(-2.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 3.96F;
        this.legLeft.z = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.1F * limbSwingAmount + 1.0F;
        this.footLeft.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 4.96F;
        this.wingRight.zRot = ageInTicks * 0.5F;
        this.wingLeft.zRot = -ageInTicks * 0.5F;
        // this.wingRight.rotateAngleZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.5F * f1 + 0.2F;
        // this.wingLeft.rotateAngleZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * f1 - 0.2F;
        // this.footRight.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount - 0.1F;
        // this.footLeft.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount - 0.1F;

        if (entityIn.eatAnimationTick > 4 && entityIn.eatAnimationTick <= 36) {
            this.neck.xRot = this.neckXRot;
        }
    }

    @Override
    public void prepareMobModel(DodoEntity p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
        this.neckXRot = p_212843_1_.getHeadEatAngleScale(p_212843_4_) + 0.5F;

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
