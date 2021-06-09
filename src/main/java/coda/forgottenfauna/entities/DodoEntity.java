package coda.forgottenfauna.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.init.FFSounds;
import net.minecraft.block.*;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class DodoEntity extends AnimalEntity implements IResurrectedEntity {
    private int cropTicks;
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    public DodoEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.2D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.MELON_SLICE), true));
        this.goalSelector.addGoal(5, new DodoEntity.RaidFarmGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, ParrotEntity.class, true));
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.DODO_SPAWN_EGG.get());
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isBaby() ? 0.5F : 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.28D);
    }

    protected SoundEvent getAmbientSound() {
        return FFSounds.DODO_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return FFSounds.DODO_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return FFSounds.DODO_HURT.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.DODO.get().create(p_241840_1_);
    }

    public void aiStep() {
        super.aiStep();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vector3d vector3d = this.getDeltaMovement();
        if (!this.onGround && vector3d.y < 0.0D) {
            this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    private boolean isCropEaten() {
        return this.cropTicks == 0;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.cropTicks > 0) {
            this.cropTicks -= this.random.nextInt(3);
            if (this.cropTicks < 0) {
                this.cropTicks = 0;
            }
        }
    }

    static class RaidFarmGoal extends MoveToBlockGoal {
        private final DodoEntity dodo;
        private boolean wantsToRaid;
        private boolean canRaid;

        public RaidFarmGoal(DodoEntity dodo) {
            super(dodo, 0.7F, 16);
            this.dodo = dodo;
        }

        public boolean canUse() {
            if (this.nextStartTick <= 0) {
                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.dodo.level, this.dodo)) {
                    return false;
                }

                this.canRaid = false;
                this.wantsToRaid = this.dodo.isCropEaten();
                this.wantsToRaid = true;
            }

            return super.canUse();
        }

        public boolean canContinueToUse() {
            return this.canRaid && super.canContinueToUse();
        }

        public void tick() {
            super.tick();
            this.dodo.getLookControl().setLookAt((double)this.blockPos.getX() + 0.5D, (double)(this.blockPos.getY() + 1), (double)this.blockPos.getZ() + 0.5D, 10.0F, (float)this.dodo.getMaxHeadXRot());
            if (this.isReachedTarget()) {
                World world = this.dodo.level;
                BlockPos blockpos = this.blockPos.above();
                BlockState blockstate = world.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (this.canRaid && block instanceof CropsBlock) {
                    Integer age = blockstate.getValue(CropsBlock.AGE);
                    if (age == 0) {
                        world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
                        world.destroyBlock(blockpos, true, this.dodo);
                    }
                    else {
                        world.setBlock(blockpos, blockstate.setValue(CropsBlock.AGE, age - 1), 2);
                        world.levelEvent(2001, blockpos, Block.getId(blockstate));
                    }

                    this.dodo.cropTicks = 40;
                }

                this.canRaid = false;
                this.nextStartTick = 10;
            }

        }

        protected boolean isValidTarget(IWorldReader worldIn, BlockPos pos) {
            Block block = worldIn.getBlockState(pos).getBlock();
            if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
                pos = pos.above();
                BlockState blockstate = worldIn.getBlockState(pos);
                block = blockstate.getBlock();
                if (block instanceof CropsBlock && ((CropsBlock)block).isMaxAge(blockstate)) {
                    this.canRaid = true;
                    return true;
                }
            }

            return false;
        }
    }
}
