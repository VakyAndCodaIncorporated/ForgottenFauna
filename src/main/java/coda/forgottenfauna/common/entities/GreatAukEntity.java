package coda.forgottenfauna.common.entities;

import coda.forgottenfauna.common.entities.ai.GroundAndSwimmerNavigator;
import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.init.FFSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class GreatAukEntity extends AnimalEntity {

    public GreatAukEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
        this.moveControl = new GreatAukEntity.MoveHelperController(this);
        this.lookControl = new DolphinLookController(this, 10);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.maxUpStep = 1.0F;
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new GroundAndSwimmerNavigator(this, level);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static boolean canAukSpawn(EntityType<? extends TameableEntity> auk, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).getBlock() == Blocks.SAND && worldIn.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new GreatAukEntity.WanderGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, CodEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SalmonEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return FFEntities.GREAT_AUK.get().create(p_241840_1_);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 480;
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

    @Override
    protected SoundEvent getAmbientSound() {
        return FFSounds.GREAT_AUK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FFSounds.GREAT_AUK_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FFSounds.GREAT_AUK_HURT.get();
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FFItems.GREAT_AUK_SPAWN_EGG.get());
    }

    static class MoveHelperController extends MovementController {
        private final GreatAukEntity auk;

        public MoveHelperController(GreatAukEntity auk) {
            super(auk);
            this.auk = auk;
        }

        public void tick() {

            if (this.operation == MovementController.Action.MOVE_TO && !this.auk.getNavigation().isDone()) {
                double d0 = this.wantedX - this.auk.getX();
                double d1 = this.wantedY - this.auk.getY();
                double d2 = this.wantedZ - this.auk.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.auk.yRot = this.rotlerp(this.auk.yRot, f, 10.0F);
                    this.auk.yBodyRot = this.auk.yRot;
                    this.auk.yHeadRot = this.auk.yRot;
                    float f1 = (float)(this.speedModifier * this.auk.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.auk.isInWater()) {
                        this.auk.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.auk.xRot = this.rotlerp(this.auk.xRot, f2, 5.0F);
                        float f3 = MathHelper.cos(this.auk.xRot * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.auk.xRot * ((float)Math.PI / 180F));
                        this.auk.zza = f3 * f1;
                        this.auk.yya = -f4 * f1;
                    } else {
                        this.auk.setSpeed(f1 * 0.45F);
                    }

                }
            } else {
                this.auk.setSpeed(0.0F);
                this.auk.setXxa(0.0F);
                this.auk.setYya(0.0F);
                this.auk.setZza(0.0F);
            }
        }
    }

    static class WanderGoal extends RandomWalkingGoal {

        private WanderGoal(GreatAukEntity auk, double speedIn, int chance) {
            super(auk, speedIn, chance);
        }

        public boolean canUse() {
            return !this.mob.isInWater() && super.canUse();
        }
    }
}
