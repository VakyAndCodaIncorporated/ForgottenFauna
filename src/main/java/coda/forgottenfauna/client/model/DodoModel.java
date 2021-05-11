package coda.forgottenfauna.client.model;

import coda.forgottenfauna.entities.DodoEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
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

    public DodoModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.legRight = new ModelRenderer(this, 54, 28);
        this.legRight.setRotationPoint(-1.0F, 4.0F, 1.0F);
        this.legRight.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legRight, 0.2617993877991494F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 44);
        this.body.setRotationPoint(0.0F, 14.5F, 0.0F);
        this.body.addBox(-5.0F, -4.5F, -6.5F, 10.0F, 9.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.2617993877991494F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 24, 32);
        this.head.setRotationPoint(0.0F, -6.5F, -2.3F);
        this.head.addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 23, 21);
        this.beak.setRotationPoint(0.0F, 1.0F, -2.5F);
        this.beak.addBox(-1.5F, -2.0F, -4.0F, 3.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 54, 28);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.0F, 4.0F, 1.0F);
        this.legLeft.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legLeft, 0.2617993877991494F, 0.0F, 0.0F);
        this.footRight = new ModelRenderer(this, 36, 29);
        this.footRight.setRotationPoint(0.0F, 5.0F, 1.0F);
        this.footRight.addBox(-4.0F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.footLeft = new ModelRenderer(this, 36, 29);
        this.footLeft.mirror = true;
        this.footLeft.setRotationPoint(0.0F, 5.0F, 1.0F);
        this.footLeft.addBox(-1.0F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.wingLeft = new ModelRenderer(this, 43, 14);
        this.wingLeft.mirror = true;
        this.wingLeft.setRotationPoint(5.0F, -2.0F, -3.0F);
        this.wingLeft.addBox(0.0F, -1.5F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.beakTip = new ModelRenderer(this, 0, 13);
        this.beakTip.setRotationPoint(0.0F, 0.5F, -3.5F);
        this.beakTip.addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(beakTip, -0.3530800996889696F, 0.0F, 0.0F);
        this.wingRight = new ModelRenderer(this, 43, 14);
        this.wingRight.setRotationPoint(-5.0F, -2.0F, -3.0F);
        this.wingRight.addBox(-1.0F, -1.5F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 25);
        this.neck.setRotationPoint(0.0F, -1.0F, -5.0F);
        this.neck.addBox(-2.5F, -10.0F, -5.0F, 5.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 39, 39);
        this.tail.setRotationPoint(0.0F, -2.0F, 3.5F);
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
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setRotationAngles(DodoEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.neck.rotationPointY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount - 1.0F;
        this.beakTip.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 0.485F;
        this.beakTip.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount - 0.35F;
        this.body.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.2F;
        this.body.rotationPointY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 14.525F;
        this.neck.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        this.tail.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        this.tail.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount - 2.0F;
        // this.wingRight.rotateAngleZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.5F * f1 + 0.2F;
        // this.wingLeft.rotateAngleZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * f1 - 0.2F;
        this.legRight.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 1.4F * limbSwingAmount + 0.2F;
        this.legRight.rotationPointY = MathHelper.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 3.96F;
        this.legRight.rotationPointZ = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 1.0F;
        this.footRight.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 4.96F;
        // this.footRight.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount - 0.1F;
        this.legLeft.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.4F * limbSwingAmount + 0.2F;
        this.legLeft.rotationPointY = MathHelper.cos(-2.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 3.96F;
        this.legLeft.rotationPointZ = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.1F * limbSwingAmount + 1.0F;
        // this.footLeft.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount - 0.1F;
        this.footLeft.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 4.96F;
        this.wingRight.rotateAngleZ = ageInTicks * 0.5F;
        this.wingLeft.rotateAngleZ = -ageInTicks * 0.5F;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
