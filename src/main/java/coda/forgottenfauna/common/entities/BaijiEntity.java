package coda.forgottenfauna.common.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import net.minecraft.block.Blocks;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class BaijiEntity extends AnimalEntity {
    private static final EntityPredicate PLAYER_PREDICATE = (new EntityPredicate()).range(10.0D).allowSameTeam().allowInvulnerable().allowUnseeable();

    public BaijiEntity(EntityType<? extends BaijiEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new BaijiEntity.MoveHelperController(this);
        this.lookControl = new DolphinLookController(this, 10);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.xRot = 0.0F;
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.BAIJI.get().create(p_241840_1_);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public static boolean canBaijiSpawn(EntityType<? extends AnimalEntity> type, IWorld worldIn, SpawnReason reason, BlockPos p_223363_3_, Random randomIn) {
        return worldIn.getBlockState(p_223363_3_).is(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.above()).is(Blocks.WATER);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        // this.goalSelector.addGoal(1, new BaijiEntity.SwimWithPlayerGoal(this, 4.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(7, new FollowBoatGoal(this));
        this.goalSelector.addGoal(8, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, GuardianEntity.class)).setAlertOthers());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 2.8D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
            this.playSound(SoundEvents.DOLPHIN_ATTACK, 1.0F, 1.0F);
        }

        return flag;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isBaby() ? 0.2F : 0.4F;
    }

    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    protected boolean canRide(Entity entityIn) {
        return true;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03D) {
            Vector3d vector3d = this.getViewVector(0.0F);
            float f = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 0.3F;
            float f1 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 0.3F;
            float f2 = 1.2F - this.random.nextFloat() * 0.7F;

            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double)f2 + (double)f, this.getY() - vector3d.y, this.getZ() - vector3d.z * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
                this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double)f2 - (double)f, this.getY() - vector3d.y, this.getZ() - vector3d.z * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 38) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addParticlesAroundSelf(IParticleData p_208401_1_) {
        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.01D;
            double d1 = this.random.nextGaussian() * 0.01D;
            double d2 = this.random.nextGaussian() * 0.01D;
            this.level.addParticle(p_208401_1_, this.getRandomX(1.5D), this.getRandomY() + 0.2D, this.getRandomZ(1.5D), d0, d1, d2);
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.BAIJI_SPAWN_EGG.get());
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.DOLPHIN_SWIM;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
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
        private final BaijiEntity baiji;

        public MoveHelperController(BaijiEntity baiji) {
            super(baiji);
            this.baiji = baiji;
        }

        public void tick() {
            if (this.baiji.isInWater()) {
                this.baiji.setDeltaMovement(this.baiji.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MovementController.Action.MOVE_TO && !this.baiji.getNavigation().isDone()) {
                double d0 = this.wantedX - this.baiji.getX();
                double d1 = this.wantedY - this.baiji.getY();
                double d2 = this.wantedZ - this.baiji.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.baiji.yRot = this.rotlerp(this.baiji.yRot, f, 10.0F);
                    this.baiji.yBodyRot = this.baiji.yRot;
                    this.baiji.yHeadRot = this.baiji.yRot;
                    float f1 = (float)(this.speedModifier * this.baiji.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.baiji.isInWater()) {
                        this.baiji.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.baiji.xRot = this.rotlerp(this.baiji.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.baiji.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.baiji.xRot * ((float)Math.PI / 180F));
                        this.baiji.zza = f3 * f1;
                        this.baiji.yya = -f4 * f1;
                    } else {
                        this.baiji.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.baiji.setSpeed(0.0F);
                this.baiji.setXxa(0.0F);
                this.baiji.setYya(0.0F);
                this.baiji.setZza(0.0F);
            }
        }
    }

    static class SwimWithPlayerGoal extends Goal {
        private final BaijiEntity baiji;
        private final double speed;
        private PlayerEntity targetPlayer;

        SwimWithPlayerGoal(BaijiEntity baijiIn, double speedIn) {
            this.baiji = baijiIn;
            this.speed = speedIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            this.targetPlayer = this.baiji.level.getNearestPlayer(BaijiEntity.PLAYER_PREDICATE, this.baiji);
            if (this.targetPlayer == null) {
                return false;
            } else {
                return this.targetPlayer.isSwimming() && this.baiji.getTarget() != this.targetPlayer;
            }
        }

        public boolean canContinueToUse() {
            return this.targetPlayer != null && this.targetPlayer.isSwimming() && this.baiji.distanceToSqr(this.targetPlayer) < 256.0D;
        }

        public void stop() {
            this.targetPlayer = null;
            this.baiji.getNavigation().stop();
        }

        public void tick() {
            this.baiji.getLookControl().setLookAt(this.targetPlayer, (float)(this.baiji.getMaxHeadYRot() + 20), (float)this.baiji.getMaxHeadXRot());
            if (this.baiji.distanceToSqr(this.targetPlayer) < 6.25D) {
                this.baiji.getNavigation().stop();
            } else {
                this.baiji.getNavigation().moveTo(this.targetPlayer, this.speed);
            }
        }
    }
}
