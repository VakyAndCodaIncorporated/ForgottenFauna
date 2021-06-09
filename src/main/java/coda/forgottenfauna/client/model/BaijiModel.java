package coda.forgottenfauna.client.model;

import coda.forgottenfauna.entities.BaijiEntity;
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
public class BaijiModel<T extends Entity> extends AgeableModel<BaijiEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer tail;
    public ModelRenderer pectoralRight;
    public ModelRenderer pectoralLeft;
    public ModelRenderer dorsalFin;
    public ModelRenderer snout;
    public ModelRenderer fluke;

    public BaijiModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.pectoralRight = new ModelRenderer(this, 1, 20);
        this.pectoralRight.setPos(-4.0F, 3.0F, -2.0F);
        this.pectoralRight.addBox(-0.5F, -1.0F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pectoralRight, 0.3490658503988659F, 0.08726646259971647F, 0.5235987755982988F);
        this.fluke = new ModelRenderer(this, 25, 12);
        this.fluke.setPos(0.0F, -0.5F, 11.0F);
        this.fluke.addBox(-6.0F, -0.5F, -2.0F, 12.0F, 1.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 1, 20);
        this.tail.setPos(0.0F, 0.5F, 7.0F);
        this.tail.addBox(-2.5F, -3.0F, -2.0F, 5.0F, 6.0F, 13.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 40);
        this.body.setPos(0.0F, 19.0F, 0.0F);
        this.body.addBox(-4.5F, -5.0F, -7.0F, 9.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralLeft = new ModelRenderer(this, 1, 20);
        this.pectoralLeft.mirror = true;
        this.pectoralLeft.setPos(4.0F, 3.0F, -2.0F);
        this.pectoralLeft.addBox(-0.5F, -1.0F, -2.5F, 1.0F, 7.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(pectoralLeft, 0.3490658503988659F, -0.08726646259971647F, -0.5235987755982988F);
        this.head = new ModelRenderer(this, 33, 41);
        this.head.setPos(0.0F, 1.0F, -6.9F);
        this.head.addBox(-3.5F, -3.0F, -6.0F, 7.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.dorsalFin = new ModelRenderer(this, 25, 22);
        this.dorsalFin.setPos(0.0F, -3.5F, 6.8F);
        this.dorsalFin.addBox(-0.5F, -2.0F, -3.5F, 1.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(dorsalFin, -0.5087634702984444F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 38, 29);
        this.snout.setPos(0.0F, 1.5F, -6.0F);
        this.snout.addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.pectoralRight);
        this.tail.addChild(this.fluke);
        this.body.addChild(this.tail);
        this.body.addChild(this.pectoralLeft);
        this.body.addChild(this.head);
        this.body.addChild(this.dorsalFin);
        this.head.addChild(this.snout);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(BaijiEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.0f;
        this.body.xRot = headPitch * ((float)Math.PI / 180F);
        this.body.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.tail.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.85F * limbSwingAmount;
        this.fluke.xRot = MathHelper.cos(-2.0F + limbSwing * speed * 0.2F) * degree * 1.2F * limbSwingAmount;
        this.body.xRot += MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.2F * limbSwingAmount;
        this.head.xRot = MathHelper.cos(2.0F + limbSwing * speed * 0.2F) * degree * 0.3F * limbSwingAmount;
        this.pectoralLeft.xRot = MathHelper.cos(-3.0F + limbSwing * speed * 0.2F) * degree * 0.8F * limbSwingAmount + 1.0F;
        this.pectoralRight.xRot = MathHelper.cos(-3.0F + limbSwing * speed * 0.2F) * degree * 0.8F * limbSwingAmount + 1.0F;
        this.body.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.15F * limbSwingAmount + 19.0F;
        this.pectoralLeft.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.2F) * degree * 0.5F * limbSwingAmount + 0.5F;
        this.pectoralRight.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.2F) * degree * -0.5F * limbSwingAmount - 0.5F;
        this.dorsalFin.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.2F) * degree * 0.25F * limbSwingAmount - 0.5F;

        if (Entity.getHorizontalDistanceSqr(entityIn.getDeltaMovement()) > 1.0E-7D) {
            this.body.xRot += -0.05F + -0.05F * MathHelper.cos(ageInTicks * 0.3F);
            this.tail.xRot = -0.1F * MathHelper.cos(ageInTicks * 0.3F);
            this.fluke.xRot = -0.2F * MathHelper.cos(ageInTicks * 0.3F);
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
