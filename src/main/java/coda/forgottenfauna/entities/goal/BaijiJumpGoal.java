package coda.forgottenfauna.entities.goal;

import coda.forgottenfauna.entities.BaijiEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class BaijiJumpGoal extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final BaijiEntity baiji;
    private final int field_220712_c;
    private boolean inWater;

    public BaijiJumpGoal(BaijiEntity baiji, int p_i50329_2_) {
        this.baiji = baiji;
        this.field_220712_c = p_i50329_2_;
    }

    public boolean shouldExecute() {
        if (this.baiji.getRNG().nextInt(this.field_220712_c) != 0) {
            return false;
        } else {
            Direction direction = this.baiji.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = this.baiji.getPosition();

            for(int k : JUMP_DISTANCES) {
                if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.add(dx * scale, 0, dz * scale);
        return this.baiji.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.baiji.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.baiji.world.getBlockState(pos.add(dx * scale, 1, dz * scale)).isAir() && this.baiji.world.getBlockState(pos.add(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean shouldContinueExecuting() {
        double d0 = this.baiji.getMotion().y;
        return (!(d0 * d0 < (double)0.03F) || this.baiji.rotationPitch == 0.0F || !(Math.abs(this.baiji.rotationPitch) < 10.0F) || !this.baiji.isInWater()) && !this.baiji.isOnGround();
    }

    public boolean isPreemptible() {
        return false;
    }

    public void startExecuting() {
        Direction direction = this.baiji.getAdjustedHorizontalFacing();
        this.baiji.setMotion(this.baiji.getMotion().add((double)direction.getXOffset() * 0.6D, 0.7D, (double)direction.getZOffset() * 0.6D));
        this.baiji.getNavigator().clearPath();
    }

    public void resetTask() {
        this.baiji.rotationPitch = 0.0F;
    }

    public void tick() {
        boolean flag = this.inWater;
        if (!flag) {
            FluidState fluidstate = this.baiji.world.getFluidState(this.baiji.getPosition());
            this.inWater = fluidstate.isTagged(FluidTags.WATER);
        }

        if (this.inWater && !flag) {
            this.baiji.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vector3d vector3d = this.baiji.getMotion();
        if (vector3d.y * vector3d.y < (double)0.03F && this.baiji.rotationPitch != 0.0F) {
            this.baiji.rotationPitch = MathHelper.rotLerp(this.baiji.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.horizontalMag(vector3d));
            double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double)(180F / (float)Math.PI);
            this.baiji.rotationPitch = (float)d1;
        }

    }
}
