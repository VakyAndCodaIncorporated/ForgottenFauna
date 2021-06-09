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
        this.texWidth = 64;
        this.texHeight = 32;
        this.body = new ModelRenderer(this, 0, 12);
        this.body.setPos(0.0F, 16.0F, 0.0F);
        this.body.addBox(-2.0F, -2.5F, -7.5F, 4.0F, 5.0F, 15.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 52, 10);
        this.legLeft.mirror = true;
        this.legLeft.setPos(1.0F, 0.0F, 5.0F);
        this.legLeft.addBox(-0.5F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 1, 12);
        this.armLeft.mirror = true;
        this.armLeft.setPos(1.5F, -1.0F, -5.0F);
        this.armLeft.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 11.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 52, 10);
        this.legRight.setPos(-1.0F, 0.0F, 5.0F);
        this.legRight.addBox(-1.5F, -1.0F, -1.5F, 2.0F, 9.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 24, 14);
        this.tail.setPos(0.0F, -1.0F, 6.5F);
        this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -1.0234610627168306F, 0.0F, 0.0F);
        this.mouth = new ModelRenderer(this, 42, 11);
        this.mouth.setPos(0.0F, 0.5F, 0.0F);
        this.mouth.addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 1, 12);
        this.armRight.setPos(-1.5F, -1.0F, -5.0F);
        this.armRight.addBox(-1.0F, -2.0F, -1.5F, 2.0F, 11.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 40, 16);
        this.snout.setPos(0.0F, 0.0F, -4.0F);
        this.snout.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 39, 28);
        this.earLeft.mirror = true;
        this.earLeft.setPos(1.5F, -2.0F, -1.5F);
        this.earLeft.addBox(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 46, 24);
        this.neck.setPos(0.0F, -1.0F, -7.5F);
        this.neck.addBox(-1.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.6981317007977318F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 2);
        this.head.setPos(0.0F, -0.5F, -0.5F);
        this.head.addBox(-2.0F, -2.5F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.6981317007977318F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 39, 28);
        this.earRight.setPos(-1.5F, -2.0F, -1.5F);
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
    public void setupAnim(ThylacineEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        limbSwingAmount = MathHelper.clamp(limbSwingAmount, -0.35f, 0.35f);

        if (entityIn.getDeltaMovement().x != 0 && entityIn.getDeltaMovement().z != 0 && !entityIn.isCrouching()) {
            this.body.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.05F;
            this.body.y = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 16F;
            this.armLeft.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 1.6F * limbSwingAmount;
            this.armLeft.y = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.07F * limbSwingAmount - 1.0F;
            this.armLeft.z = -5.0F;
            this.armRight.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.6F * limbSwingAmount;
            this.armRight.y = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.07F * limbSwingAmount - 1.0F;
            this.armRight.z = -5.0F;
            this.legRight.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.4F * limbSwingAmount + 0.15F;
            this.legRight.z = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 5.0F;
            this.legLeft.xRot = MathHelper.cos(-2.0F + limbSwing * speed * 0.4F) * degree * -1.4F * limbSwingAmount + 0.15F;
            this.legLeft.z = MathHelper.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 5.0F;
            this.tail.xRot = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.75F * limbSwingAmount - 0.4F;
            this.tail.yRot = 0.0F;
            this.neck.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.38F;
            this.neck.y = -1.0F;
            this.head.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount + 0.65F;
            this.head.y = -0.5F;
            this.earRight.xRot = 0.0F;
            this.earRight.zRot = 0.0F;
            this.earLeft.xRot = 0.0F;
            this.earLeft.zRot = 0.0F;
        }
        else if(entityIn.isCrouching()) {
            this.body.xRot = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 0.1F;
            this.legRight.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.1F;
            this.legLeft.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.6F * limbSwingAmount - 0.1F;
            this.body.y = limbSwingAmount + 16.1F;
            this.neck.xRot = limbSwingAmount + 0.5F;
            this.head.xRot = limbSwingAmount - 0.5F;
            this.neck.y = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.1F * limbSwingAmount + 0.1F;
            this.head.y = limbSwingAmount + 0.8F;
            this.tail.xRot = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.5F * limbSwingAmount - 1.25F;
            this.armLeft.y = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount - 1.0F;
            this.armLeft.z = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount - 5.0F;
            this.armLeft.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount - 0.1F;
            this.armRight.y = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount - 1.0F;
            this.armRight.z = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount - 5.0F;
            this.armRight.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.6F * limbSwingAmount - 0.1F;
            this.tail.yRot = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.4F * limbSwingAmount;
            this.legRight.y = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 0.5F;
            this.legRight.z = MathHelper.cos(limbSwing * speed * 0.1F) * degree * 0.2F * limbSwingAmount + 5.0F;
            this.legLeft.y = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount + 0.5F;
            this.legLeft.z = MathHelper.cos(limbSwing * speed * 0.1F) * degree * -0.2F * limbSwingAmount + 5.0F;
            this.earRight.xRot = limbSwingAmount - 0.5F;
            this.earRight.zRot = -2.0F * limbSwingAmount;
            this.earLeft.xRot = limbSwingAmount - 0.5F;
            this.earLeft.zRot =  2.0F * limbSwingAmount;
        }
        else {
            this.body.xRot = 0.0F;
            this.body.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * 0.05F + 16.0F;
            this.armLeft.xRot = 0.0F;
            this.armLeft.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * -0.05F - 1.0F;
            this.armLeft.z = -5.0F;
            this.armRight.xRot = 0.0F;
            this.armRight.z = -5.0F;
            this.armRight.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * -0.05F - 1.0F;
            this.legRight.xRot = 0.0F;
            this.legRight.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * -0.05F;
            this.legRight.z = 5.0F;
            this.legLeft.xRot = 0.0F;
            this.legLeft.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * -0.05F;
            this.legLeft.z = 5.0F;
            this.tail.yRot = MathHelper.cos(entityIn.tickCount * speed * 0.2F) * 0.25F * 2.0F;
            this.tail.xRot = -1.0234610627168306F;
            this.neck.xRot = -0.6981317007977318F;
            this.neck.y = -1.0F;
            this.head.y = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * 0.05F - 0.5F;
            this.head.xRot = MathHelper.cos(entityIn.tickCount * speed * 0.3F) * degree * 0.05F + 0.75F;
            this.earRight.xRot = 0.0F;
            this.earRight.zRot = 0.0F;
            this.earLeft.xRot = 0.0F;
            this.earLeft.zRot = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.EMPTY_LIST;
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
}
