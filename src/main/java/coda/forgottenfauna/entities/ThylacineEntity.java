package coda.forgottenfauna.entities;

import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ThylacineEntity extends AnimalEntity {
    private static final DataParameter<Boolean> STALKING = EntityDataManager.createKey(ThylacineEntity.class, DataSerializers.BOOLEAN);

    public ThylacineEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.RABBIT), true));
        this.goalSelector.addGoal(5, new ThylacineEntity.StalkAndAttackGoal(this));
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
        return isChild() ? 0.3F : 0.6F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        ThylacineEntity attacker = this;
        if (attacker.getAttackTarget() instanceof RabbitEntity || attacker.getAttackTarget() instanceof ChickenEntity || attacker.getAttackTarget() instanceof ParrotEntity) {
            entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
        }

        return flag;
    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.THYLACINE.get().create(p_241840_1_);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.RABBIT;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(STALKING, false);
    }

    public boolean isStalking() {
        return this.dataManager.get(STALKING);
    }

    public void setStalking(boolean stalking) {
        this.dataManager.set(STALKING, stalking);
        this.setAIMoveSpeed(0.00005F);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Stalking", this.isStalking());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setStalking(compound.getBoolean("Stalking"));
    }

    static class StalkAndAttackGoal extends MeleeAttackGoal {
        private final ThylacineEntity entity;

        public StalkAndAttackGoal(ThylacineEntity entity) {
            super(entity, 0.5D, true);
            this.entity = entity;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (entity.getAttackTarget() != null) {
                if (entity.getAttackTarget().getDistanceSq(entity) < 50.0D) {
                    entity.setStalking(true);
                }
            }
            return super.shouldContinueExecuting();
        }

        @Override
        public void resetTask() {
            super.resetTask();
            if (entity.getAttackTarget() == null) {
                entity.setStalking(false);
            }
        }
    }
}
