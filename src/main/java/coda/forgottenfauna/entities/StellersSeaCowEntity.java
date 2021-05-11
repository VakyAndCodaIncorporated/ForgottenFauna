package coda.forgottenfauna.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.init.FFSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class StellersSeaCowEntity extends AnimalEntity implements IResurrectedEntity {
    public float prevTilt;
    public float tilt;

    public StellersSeaCowEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 1));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, GuardianEntity.class)).setCallsForHelp());

    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        prevTilt = tilt;
        if (isInWater()) {
            final float v = MathHelper.wrapSubtractDegrees(rotationYaw, prevRotationYaw);
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
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.STELLERS_SEA_COW.get().create(p_241840_1_);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 80.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected PathNavigator createNavigator(World worldIn) {
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

    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DOLPHIN_SWIM;
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
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
                this.seaCow.setMotion(this.seaCow.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.seaCow.getNavigator().noPath()) {
                double d0 = this.posX - this.seaCow.getPosX();
                double d1 = this.posY - this.seaCow.getPosY();
                double d2 = this.posZ - this.seaCow.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setMoveForward(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.seaCow.rotationYaw = this.limitAngle(this.seaCow.rotationYaw, f, 10.0F);
                    this.seaCow.renderYawOffset = this.seaCow.rotationYaw;
                    this.seaCow.rotationYawHead = this.seaCow.rotationYaw;
                    float f1 = (float)(this.speed * this.seaCow.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.seaCow.isInWater()) {
                        this.seaCow.setAIMoveSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.seaCow.rotationPitch = this.limitAngle(this.seaCow.rotationPitch, f2, 5.0F);
                        float f3 = MathHelper.cos(this.seaCow.rotationPitch * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.seaCow.rotationPitch * ((float)Math.PI / 180F));
                        this.seaCow.moveForward = f3 * f1;
                        this.seaCow.moveVertical = -f4 * f1;
                    } else {
                        this.seaCow.setAIMoveSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.seaCow.setAIMoveSpeed(0.0F);
                this.seaCow.setMoveStrafing(0.0F);
                this.seaCow.setMoveVertical(0.0F);
                this.seaCow.setMoveForward(0.0F);
            }
        }
    }
}
