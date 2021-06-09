package coda.forgottenfauna.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.init.FFSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ThylacineEntity extends AnimalEntity implements IResurrectedEntity {

    public ThylacineEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        // this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(Items.RABBIT), true));
        this.goalSelector.addGoal(5, new OcelotAttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, ParrotEntity.class, true));
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.THYLACINE_SPAWN_EGG.get());
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isBaby() ? 0.3F : 0.6F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.35D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
            playSound(FFSounds.THYLACINE_ATTACK.get(), 0.4F, 1.0F);
        }

        ThylacineEntity attacker = this;
        if (attacker.getTarget() instanceof RabbitEntity || attacker.getTarget() instanceof ChickenEntity || attacker.getTarget() instanceof ParrotEntity) {
            entityIn.hurt(DamageSource.mobAttack(this), 10.0F);
        }

        return flag;
    }

    protected SoundEvent getAmbientSound() {
        return FFSounds.THYLACINE_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return FFSounds.THYLACINE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return FFSounds.THYLACINE_HURT.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.THYLACINE.get().create(p_241840_1_);
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double speed = this.getMoveControl().getSpeedModifier();
            if (speed == 0.6D) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            }
            else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }
}
