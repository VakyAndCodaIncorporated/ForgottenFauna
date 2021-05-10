package coda.forgottenfauna.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class BaijiEntity extends AnimalEntity {
    private static final EntityPredicate field_213810_bA = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable().setLineOfSiteRequired();

    public BaijiEntity(EntityType<? extends BaijiEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new BaijiEntity.MoveHelperController(this);
        this.lookController = new DolphinLookController(this, 10);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.rotationPitch = 0.0F;
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.BAIJI.get().create(p_241840_1_);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(1, new BaijiEntity.SwimWithPlayerGoal(this, 4.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(7, new FollowBoatGoal(this));
        this.goalSelector.addGoal(8, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, GuardianEntity.class)).setCallsForHelp());
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SALMON;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.2D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
            this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0F, 1.0F);
        }

        return flag;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isChild() ? 0.2F : 0.4F;
    }

    public int getVerticalFaceSpeed() {
        return 1;
    }

    public int getHorizontalFaceSpeed() {
        return 1;
    }

    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    public void tick() {
        super.tick();
        if (this.world.isRemote && this.isInWater() && this.getMotion().lengthSquared() > 0.03D) {
            Vector3d vector3d = this.getLook(0.0F);
            float f = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * 0.3F;
            float f1 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)) * 0.3F;
            float f2 = 1.2F - this.rand.nextFloat() * 0.7F;

            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.DOLPHIN, this.getPosX() - vector3d.x * (double)f2 + (double)f, this.getPosY() - vector3d.y, this.getPosZ() - vector3d.z * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
                this.world.addParticle(ParticleTypes.DOLPHIN, this.getPosX() - vector3d.x * (double)f2 - (double)f, this.getPosY() - vector3d.y, this.getPosZ() - vector3d.z * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 38) {
            this.func_208401_a(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void func_208401_a(IParticleData p_208401_1_) {
        for(int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.01D;
            double d1 = this.rand.nextGaussian() * 0.01D;
            double d2 = this.rand.nextGaussian() * 0.01D;
            this.world.addParticle(p_208401_1_, this.getPosXRandom(1.5D), this.getPosYRandom() + 0.2D, this.getPosZRandom(1.5D), d0, d1, d2);
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.BAIJI_SPAWN_EGG.get());
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
    }

    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_DOLPHIN_SPLASH;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DOLPHIN_SWIM;
    }

    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
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
        private final BaijiEntity baiji;

        public MoveHelperController(BaijiEntity baijiIn) {
            super(baijiIn);
            this.baiji = baijiIn;
        }

        public void tick() {
            if (this.baiji.isInWater()) {
                this.baiji.setMotion(this.baiji.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.baiji.getNavigator().noPath()) {
                double d0 = this.posX - this.baiji.getPosX();
                double d1 = this.posY - this.baiji.getPosY();
                double d2 = this.posZ - this.baiji.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setMoveForward(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.baiji.rotationYaw = this.limitAngle(this.baiji.rotationYaw, f, 10.0F);
                    this.baiji.renderYawOffset = this.baiji.rotationYaw;
                    this.baiji.rotationYawHead = this.baiji.rotationYaw;
                    float f1 = (float)(this.speed * this.baiji.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.baiji.isInWater()) {
                        this.baiji.setAIMoveSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.baiji.rotationPitch = this.limitAngle(this.baiji.rotationPitch, f2, 5.0F);
                        float f3 = MathHelper.cos(this.baiji.rotationPitch * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.baiji.rotationPitch * ((float)Math.PI / 180F));
                        this.baiji.moveForward = f3 * f1;
                        this.baiji.moveVertical = -f4 * f1;
                    } else {
                        this.baiji.setAIMoveSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.baiji.setAIMoveSpeed(0.0F);
                this.baiji.setMoveStrafing(0.0F);
                this.baiji.setMoveVertical(0.0F);
                this.baiji.setMoveForward(0.0F);
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
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean shouldExecute() {
            this.targetPlayer = this.baiji.world.getClosestPlayer(BaijiEntity.field_213810_bA, this.baiji);
            if (this.targetPlayer == null) {
                return false;
            } else {
                return this.targetPlayer.isSwimming() && this.baiji.getAttackTarget() != this.targetPlayer;
            }
        }

        public boolean shouldContinueExecuting() {
            return this.targetPlayer != null && this.targetPlayer.isSwimming() && this.baiji.getDistanceSq(this.targetPlayer) < 256.0D;
        }

        public void resetTask() {
            this.targetPlayer = null;
            this.baiji.getNavigator().clearPath();
        }

        public void tick() {
            this.baiji.getLookController().setLookPositionWithEntity(this.targetPlayer, (float)(this.baiji.getHorizontalFaceSpeed() + 20), (float)this.baiji.getVerticalFaceSpeed());
            if (this.baiji.getDistanceSq(this.targetPlayer) < 6.25D) {
                this.baiji.getNavigator().clearPath();
            } else {
                this.baiji.getNavigator().tryMoveToEntityLiving(this.targetPlayer, this.speed);
            }
        }
    }
}
