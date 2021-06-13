package coda.forgottenfauna.common.entities.ai;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.common.entities.DodoEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.List;

public class DodoForageGoal extends Goal {
    private static final ResourceLocation FORAGING_LOOT = new ResourceLocation(ForgottenFauna.MOD_ID, "gameplay/dodo_foraging");
    private final DodoEntity dodo;
    private final World level;
    private int eatAnimationTick;

    public DodoForageGoal(DodoEntity p_i45314_1_) {
        this.dodo = p_i45314_1_;
        this.level = p_i45314_1_.level;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.dodo.getRandom().nextInt(this.dodo.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.dodo.blockPosition();
            return this.level.getBlockState(blockpos.below()).is(BlockTags.LEAVES);
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = 40;
        this.level.broadcastEntityEvent(this.dodo, (byte)10);
        this.dodo.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == 4) {
            BlockPos blockpos = this.dodo.blockPosition();
            BlockPos blockpos1 = blockpos.below();
            if (this.level.getBlockState(blockpos1).is(BlockTags.LEAVES)) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.dodo)) {
                    this.level.levelEvent(2001, blockpos1, Block.getId(Blocks.OAK_LEAVES.defaultBlockState()));
                }
                this.dodo.ate();
                List<ItemStack> items = dodo.level.getServer().getLootTables().get(FORAGING_LOOT).getRandomItems(new LootContext.Builder((ServerWorld) dodo.level).withRandom(dodo.getRandom()).create(LootParameterSets.EMPTY));
                InventoryHelper.dropContents(dodo.level, blockpos, NonNullList.of(ItemStack.EMPTY, items.toArray(new ItemStack[0])));
            }
        }
    }
}
