package coda.forgottenfauna.client.model;

import coda.forgottenfauna.entities.StellersSeaCowEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class StellersSeaCowModel<T extends Entity> extends AgeableModel<StellersSeaCowEntity> {
    public ModelRenderer body;
    public ModelRenderer tail1;
    public ModelRenderer pectoralLeft;
    public ModelRenderer pectoralRight;
    public ModelRenderer neck;
    public ModelRenderer tail2;
    public ModelRenderer flukeLeft;
    public ModelRenderer flukeRight;
    public ModelRenderer head;
    public ModelRenderer snoutRight;
    public ModelRenderer snoutLeft;
    public ModelRenderer mouth;

    public StellersSeaCowModel() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.neck = new ModelRenderer(this, 74, 60);
        this.neck.setRotationPoint(0.0F, -3.2F, -22.0F);
        this.neck.addBox(-10.0F, -10.0F, -13.0F, 20.0F, 22.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.snoutLeft = new ModelRenderer(this, 126, 143);
        this.snoutLeft.mirror = true;
        this.snoutLeft.setRotationPoint(2.5F, -1.0F, -12.0F);
        this.snoutLeft.addBox(-3.0F, -3.0F, -7.0F, 8.0F, 11.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snoutLeft, 0.0F, 0.0F, -0.19547687289441354F);
        this.tail1 = new ModelRenderer(this, 0, 119);
        this.tail1.setRotationPoint(0.0F, -2.0F, 21.0F);
        this.tail1.addBox(-10.5F, -10.5F, -5.0F, 21.0F, 21.0F, 39.0F, 0.0F, 0.0F, 0.0F);
        this.flukeLeft = new ModelRenderer(this, 102, 204);
        this.flukeLeft.setRotationPoint(7.0F, 0.0F, 32.0F);
        this.flukeLeft.addBox(-4.0F, -1.0F, -8.0F, 28.0F, 2.0F, 17.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(flukeLeft, 0.0F, -0.5478588415483757F, 0.0F);
        this.pectoralRight = new ModelRenderer(this, 6, 125);
        this.pectoralRight.setRotationPoint(-13.0F, 10.0F, -17.5F);
        this.pectoralRight.addBox(-1.0F, -2.0F, -4.5F, 2.0F, 18.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pectoralRight, 0.19547687289441354F, -0.0781907508222411F, 0.23457224414434488F);
        this.snoutRight = new ModelRenderer(this, 126, 143);
        this.snoutRight.setRotationPoint(-2.5F, -1.0F, -12.0F);
        this.snoutRight.addBox(-5.0F, -3.0F, -7.0F, 8.0F, 11.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(snoutRight, 0.0F, 0.0F, 0.19547687289441354F);
        this.body = new ModelRenderer(this, 0, 181);
        this.body.setRotationPoint(0.0F, 8.0F, -2.5F);
        this.body.addBox(-14.0F, -14.0F, -23.0F, 28.0F, 30.0F, 44.0F, 0.0F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 0, 60);
        this.tail2.setRotationPoint(0.0F, -2.0F, 34.0F);
        this.tail2.addBox(-7.5F, -7.0F, -5.0F, 15.0F, 16.0F, 41.0F, 0.0F, 0.0F, 0.0F);
        this.flukeRight = new ModelRenderer(this, 102, 204);
        this.flukeRight.mirror = true;
        this.flukeRight.setRotationPoint(-7.0F, 0.0F, 32.0F);
        this.flukeRight.addBox(-24.0F, -1.0F, -8.0F, 28.0F, 2.0F, 17.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(flukeRight, 0.0F, 0.5473352640780661F, 0.0F);
        this.mouth = new ModelRenderer(this, 91, 135);
        this.mouth.setRotationPoint(0.0F, 6.0F, -9.0F);
        this.mouth.addBox(-3.5F, -2.0F, -9.0F, 7.0F, 4.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 115, 168);
        this.head.setRotationPoint(0.0F, -0.5F, -14.0F);
        this.head.addBox(-7.5F, -6.5F, -12.0F, 15.0F, 15.0F, 15.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralLeft = new ModelRenderer(this, 6, 125);
        this.pectoralLeft.mirror = true;
        this.pectoralLeft.setRotationPoint(13.0F, 9.0F, -16.0F);
        this.pectoralLeft.addBox(-1.0F, -2.0F, -4.5F, 2.0F, 18.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pectoralLeft, 0.19547687289441354F, 0.0781907508222411F, -0.23457224414434488F);
        this.body.addChild(this.neck);
        this.head.addChild(this.snoutLeft);
        this.body.addChild(this.tail1);
        this.tail2.addChild(this.flukeLeft);
        this.body.addChild(this.pectoralRight);
        this.head.addChild(this.snoutRight);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.flukeRight);
        this.head.addChild(this.mouth);
        this.neck.addChild(this.head);
        this.body.addChild(this.pectoralLeft);
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
    public void setRotationAngles(StellersSeaCowEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.body.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.2F * limbSwingAmount;
        this.body.rotationPointY = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.15F * limbSwingAmount + 8.0F;
        this.neck.rotateAngleX = MathHelper.cos(2.0F + limbSwing * speed * 0.2F) * degree * 0.15F * limbSwingAmount;
        this.pectoralLeft.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.4F * limbSwingAmount + 1.0F;
        this.pectoralRight.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.4F * limbSwingAmount + 1.0F;
        this.pectoralLeft.rotateAngleY = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.3F * limbSwingAmount + 0.5F;
        this.pectoralRight.rotateAngleY = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * -0.3F * limbSwingAmount - 0.5F;
        this.head.rotateAngleX = MathHelper.cos(2.0F + limbSwing * speed * 0.2F) * degree * 0.3F * limbSwingAmount;
        this.tail1.rotateAngleX = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.3F * limbSwingAmount - 0.1F;
        this.tail2.rotateAngleX = MathHelper.cos(-2.0F + limbSwing * speed * 0.2F) * degree * 0.35F * limbSwingAmount - 0.05F;
        this.flukeLeft.rotateAngleX = MathHelper.cos(-2.0F + limbSwing * speed * 0.2F) * degree * 0.5F * limbSwingAmount;
        this.flukeRight.rotateAngleX = MathHelper.cos(-2.0F + limbSwing * speed * 0.2F) * degree * 0.5F * limbSwingAmount;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
