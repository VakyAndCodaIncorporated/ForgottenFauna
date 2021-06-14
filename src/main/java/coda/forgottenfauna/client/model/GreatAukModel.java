package coda.forgottenfauna.client.model;

import coda.forgottenfauna.common.entities.GreatAukEntity;
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
public class GreatAukModel<T extends Entity> extends AgeableModel<GreatAukEntity> {
    public ModelRenderer body;
    public ModelRenderer legLeft;
    public ModelRenderer tail;
    public ModelRenderer legRight;
    public ModelRenderer neck;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer footLeft;
    public ModelRenderer footRight;
    public ModelRenderer head;
    public ModelRenderer beak;
    public ModelRenderer mouth;

    public GreatAukModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.mouth = new ModelRenderer(this, 43, 53);
        this.mouth.setPos(0.0F, 0.5F, -5.5F);
        this.mouth.addBox(-1.0F, -0.5F, -4.5F, 2.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 55, 48);
        this.legLeft.mirror = true;
        this.legLeft.setPos(1.5F, 6.5F, 1.0F);
        this.legLeft.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legLeft, -1.5707963267948966F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 42);
        this.body.setPos(0.0F, 15.5F, 0.0F);
        this.body.addBox(-3.5F, -6.5F, -4.0F, 7.0F, 13.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.footLeft = new ModelRenderer(this, 48, 42);
        this.footLeft.mirror = true;
        this.footLeft.setPos(0.0F, 3.5F, 1.0F);
        this.footLeft.addBox(-1.5F, 0.0F, -1.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(footLeft, -1.5707963267948966F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 55, 48);
        this.legRight.setPos(-1.5F, 6.5F, 1.0F);
        this.legRight.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legRight, -1.5707963267948966F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 1, 30);
        this.neck.setPos(0.0F, -4.7F, -1.5F);
        this.neck.addBox(-2.0F, -7.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.7853981633974483F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 31, 55);
        this.beak.setPos(0.0F, -1.5F, -5.5F);
        this.beak.addBox(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.footRight = new ModelRenderer(this, 48, 42);
        this.footRight.setPos(0.0F, 3.5F, 1.0F);
        this.footRight.addBox(-3.5F, 0.0F, -1.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(footRight, -1.5707963267948966F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 23, 37);
        this.head.setPos(0.0F, -6.5F, 1.5F);
        this.head.addBox(-2.5F, -4.0F, -6.0F, 5.0F, 5.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.7853981633974483F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 41, 33);
        this.tail.setPos(0.0F, 5.4F, 3.0F);
        this.tail.addBox(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -0.2617993877991494F, 0.0F, 0.0F);
        this.wingRight = new ModelRenderer(this, 19, 31);
        this.wingRight.setPos(-3.5F, -5.0F, 1.0F);
        this.wingRight.addBox(-1.0F, -1.5F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(wingRight, 0.5235987755982988F, 0.0F, 0.0F);
        this.wingLeft = new ModelRenderer(this, 19, 31);
        this.wingLeft.setPos(3.5F, -5.0F, 1.0F);
        this.wingLeft.addBox(0.0F, -1.5F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(wingLeft, 0.5235987755982988F, 0.0F, 0.0F);
        this.head.addChild(this.mouth);
        this.body.addChild(this.legLeft);
        this.legLeft.addChild(this.footLeft);
        this.body.addChild(this.legRight);
        this.body.addChild(this.neck);
        this.head.addChild(this.beak);
        this.legRight.addChild(this.footRight);
        this.neck.addChild(this.head);
        this.body.addChild(this.tail);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.wingLeft);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(GreatAukEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 3.0f;
        float degree = 1.0f;


        limbSwingAmount = MathHelper.clamp(limbSwingAmount, -0.5f, 0.5f);
        if (entityIn.isInWater()) {
            this.body.xRot = headPitch * ((float)Math.PI / 180F);
            this.body.yRot = netHeadYaw * ((float)Math.PI / 180F);
            this.body.xRot += limbSwingAmount + 1.25F;
            this.body.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.body.y = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 15.5F;
            this.head.xRot = -0.25F;
            this.head.z = 1.5F;
            this.tail.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount - 1.3F;
            this.neck.xRot = -1.2F;
            this.neck.z = -1.5F;
            this.wingRight.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.2F;
            this.wingRight.zRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.1F;
            this.wingLeft.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.2F;
            this.wingLeft.zRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * -0.2F * limbSwingAmount - 0.1F;
            this.legRight.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 1.3F;
            this.legRight.yRot = 0.0F;
            this.legLeft.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 1.3F;
            this.legLeft.yRot = 0.0F;
        }
        else {
            this.body.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.body.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.body.xRot = 0.0F;
            this.body.y = 15.5F;
            this.legRight.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 1.575F;
            this.legRight.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
            this.legLeft.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.6F * limbSwingAmount - 1.575F;
            this.legLeft.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.2F;
            this.tail.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.3F * limbSwingAmount - 0.25F;
            this.neck.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.15F * limbSwingAmount - 0.75F;
            this.neck.z = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount - 1.5F;
            this.head.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.75F;
            this.head.z = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.075F * limbSwingAmount + 1.5F;
            this.wingRight.zRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.15F;
            this.wingRight.xRot = 0.0F;
            this.wingLeft.zRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.15F;
            this.wingLeft.xRot = 0.0F;
        }
    }
}

