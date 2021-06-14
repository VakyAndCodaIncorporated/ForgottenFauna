package coda.forgottenfauna.common.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.init.FFSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class StellersSeaCowEntity extends AnimalEntity {
    public float prevTilt;
    public float tilt;

    public StellersSeaCowEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MoveHelperController(this);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.lookControl = new DolphinLookController(this, 10);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, GuardianEntity.class)).setAlertOthers());
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        prevTilt = tilt;
        if (isInWater()) {
            final float v = MathHelper.degreesDifference(yRot, yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt += Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 3;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.STELLERS_SEA_COW.get().create(p_241840_1_);
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 80.0D).add(Attributes.MOVEMENT_SPEED, 0.17D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FFSounds.STELLERS_SEA_COW_HURT.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return FFSounds.STELLERS_SEA_COW_DEATH.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.STELLERS_SEA_COW_SPAWN_EGG.get());
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.DOLPHIN_SWIM;
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    static class MoveHelperController extends MovementController {
        private final StellersSeaCowEntity seaCow;

        public MoveHelperController(StellersSeaCowEntity seaCow) {
            super(seaCow);
            this.seaCow = seaCow;
        }

        public void tick() {
            if (this.seaCow.isInWater()) {
                this.seaCow.setDeltaMovement(this.seaCow.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.seaCow.getNavigation().isDone()) {
                double d0 = this.wantedX - this.seaCow.getX();
                double d1 = this.wantedY - this.seaCow.getY();
                double d2 = this.wantedZ - this.seaCow.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.seaCow.yRot = this.rotlerp(this.seaCow.yRot, f, 10.0F);
                    this.seaCow.yBodyRot = this.seaCow.yRot;
                    this.seaCow.yHeadRot = this.seaCow.yRot;
                    float f1 = (float)(this.speedModifier * this.seaCow.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.seaCow.isInWater()) {
                        this.seaCow.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.seaCow.xRot = this.rotlerp(this.seaCow.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.seaCow.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.seaCow.xRot * ((float)Math.PI / 180F));
                        this.seaCow.zza = f3 * f1;
                        this.seaCow.yya = -f4 * f1;
                    } else {
                        this.seaCow.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.seaCow.setSpeed(0.0F);
                this.seaCow.setXxa(0.0F);
                this.seaCow.setYya(0.0F);
                this.seaCow.setZza(0.0F);
            }
        }
    }
}
