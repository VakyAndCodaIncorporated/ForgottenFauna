package coda.forgottenfauna.client.model;

import coda.forgottenfauna.entities.ThylacineEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class ThylacineModel<T extends Entity> extends AgeableModel<ThylacineEntity> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer neck;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer head;
    public ModelRenderer snout;
    public ModelRenderer earLeft;
    public ModelRenderer earRight;
    public ModelRenderer mouth;

    public ThylacineModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 12);
        this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.body.addBox(-2.0F, -2.5F, -7.5F, 4.0F, 5.0F, 15.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 52, 10);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(1.0F, 0.0F, 5.0F);
        this.legLeft.addBox(-0.5F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 1, 12);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.5F, -1.0F, -5.0F);
        this.armLeft.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 11.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 52, 10);
        this.legRight.setRotationPoint(-1.0F, 0.0F, 5.0F);
        this.legRight.addBox(-1.5F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 24, 14);
        this.tail.setRotationPoint(0.0F, -1.0F, 6.5F);
        this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -1.0234610627168306F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 42, 11);
        this.mouth.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.mouth.addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 1, 12);
        this.armRight.setRotationPoint(-1.5F, -1.0F, -5.0F);
        this.armRight.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 11.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 40, 16);
        this.snout.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.snout.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 39, 28);
        this.earLeft.mirror = true;
        this.earLeft.setRotationPoint(1.5F, -2.0F, -1.5F);
        this.earLeft.addBox(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 46, 24);
        this.neck.setRotationPoint(0.0F, -1.0F, -7.5F);
        this.neck.addBox(-1.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.6981317007977318F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 2);
        this.head.setRotationPoint(0.0F, -0.5F, -0.5F);
        this.head.addBox(-2.0F, -2.5F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.6981317007977318F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 39, 28);
        this.earRight.setRotationPoint(-1.5F, -2.0F, -1.5F);
        this.earRight.addBox(-1.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.legRight);
        this.body.addChild(this.tail);
        this.snout.addChild(this.mouth);
        this.body.addChild(this.armRight);
        this.head.addChild(this.snout);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.neck);
        this.neck.addChild(this.head);
        this.head.addChild(this.earRight);
    }

    @Override
    public void setRotationAngles(ThylacineEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        limbSwingAmount = MathHelper.clamp(limbSwingAmount, -0.35f, 0.35f);

        if (entityIn.getMotion().x != 0 && entityIn.getMotion().z != 0 && !entityIn.isStalking()) {
            this.body.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.05F;
            this.body.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 16F;
            this.armLeft.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 1.6F * limbSwingAmount;
            this.armLeft.rotationPointY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.07F * limbSwingAmount - 1.0F;
            this.armLeft.rotationPointZ = -5.0F;
            this.armRight.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.6F * limbSwingAmount;
            this.armRight.rotationPointY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.07F * limbSwingAmount - 1.0F;
            this.armRight.rotationPointZ = -5.0F;
            this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.4F * limbSwingAmount + 0.15F;
            this.legRight.rotationPointZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 5.0F;
            this.legLeft.rotateAngleX = MathHelper.cos(-2.0F + limbSwing * speed * 0.4F) * degree * -1.4F * limbSwingAmount + 0.15F;
            this.legLeft.rotationPointZ = MathHelper.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 5.0F;
            this.tail.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.75F * limbSwingAmount - 0.4F;
            this.tail.rotateAngleY = 0.0F;
            this.neck.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.38F;
            this.neck.rotationPointY = -1.0F;
            this.head.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 0.65F;
            this.head.rotationPointY = -0.5F;
            this.earRight.rotateAngleX = 0.0F;
            this.earRight.rotateAngleZ = 0.0F;
            this.earLeft.rotateAngleX = 0.0F;
            this.earLeft.rotateAngleZ = 0.0F;
        }
        else if(entityIn.isStalking()) {
            this.body.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 0.1F;
            this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.1F;
            this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.6F * limbSwingAmount - 0.1F;
            this.body.rotationPointY = limbSwingAmount + 16.1F;
            this.neck.rotateAngleX = limbSwingAmount + 0.5F;
            this.head.rotateAngleX = limbSwingAmount - 0.5F;
            this.neck.rotationPointY = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.1F * limbSwingAmount + 0.1F;
            this.head.rotationPointY = limbSwingAmount + 0.8F;
            this.tail.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.5F * limbSwingAmount - 1.25F;
            this.armLeft.rotationPointY = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount - 1.0F;
            this.armLeft.rotationPointZ = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount - 5.0F;
            this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.1F;
            this.armRight.rotationPointY = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount - 1.0F;
            this.armRight.rotationPointZ = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount - 5.0F;
            this.armRight.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.6F * limbSwingAmount - 0.1F;
            this.tail.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.4F * limbSwingAmount;
            this.legRight.rotationPointY = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 0.5F;
            this.legRight.rotationPointZ = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 5.0F;
            this.legLeft.rotationPointY = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount + 0.5F;
            this.legLeft.rotationPointZ = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount + 5.0F;
            this.earRight.rotateAngleX = limbSwingAmount - 0.5F;
            this.earRight.rotateAngleZ = -2.0F * limbSwingAmount;
            this.earLeft.rotateAngleX = limbSwingAmount - 0.5F;
            this.earLeft.rotateAngleZ =  2.0F * limbSwingAmount;
        }
        else {
            this.body.rotateAngleX = 0.0F;
            this.body.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * 0.05F + 16.0F;
            this.armLeft.rotateAngleX = 0.0F;
            this.armLeft.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * -0.05F - 1.0F;
            this.armLeft.rotationPointZ = -5.0F;
            this.armRight.rotateAngleX = 0.0F;
            this.armRight.rotationPointZ = -5.0F;
            this.armRight.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * -0.05F - 1.0F;
            this.legRight.rotateAngleX = 0.0F;
            this.legRight.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * -0.05F;
            this.legRight.rotationPointZ = 5.0F;
            this.legLeft.rotateAngleX = 0.0F;
            this.legLeft.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * -0.05F;
            this.legLeft.rotationPointZ = 5.0F;
            this.tail.rotateAngleY = MathHelper.cos(entityIn.ticksExisted * speed * 0.2F) * 0.25F * 2.0F;
            this.tail.rotateAngleX = -1.0234610627168306F;
            this.neck.rotateAngleX = -0.6981317007977318F;
            this.neck.rotationPointY = -1.0F;
            this.head.rotationPointY = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * 0.05F - 0.5F;
            this.head.rotateAngleX = MathHelper.cos(entityIn.ticksExisted * speed * 0.3F) * degree * 0.05F + 0.75F;
            this.earRight.rotateAngleX = 0.0F;
            this.earRight.rotateAngleZ = 0.0F;
            this.earLeft.rotateAngleX = 0.0F;
            this.earLeft.rotateAngleZ = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
