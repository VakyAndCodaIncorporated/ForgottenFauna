package coda.forgottenfauna.world;

import coda.forgottenfauna.ForgottenFauna;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ResurrectionEventCapability implements ICapabilitySerializable<IntNBT> {
    @CapabilityInject(ResurrectionEventHandler.class)
    public static Capability<ResurrectionEventHandler> capability;
    public static final ResourceLocation ID = new ResourceLocation(ForgottenFauna.MOD_ID, "resurrection_event");

    private final ResurrectionEventHandler handler = new ResurrectionEventHandler();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == capability ? LazyOptional.of(() -> handler).cast() : LazyOptional.empty();
    }

    @Override
    public IntNBT serializeNBT() {
        return IntNBT.valueOf(handler.getTicks());
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        handler.setTicks(nbt.getAsInt());
    }

    public static class Storage implements Capability.IStorage<ResurrectionEventHandler> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ResurrectionEventHandler> capability, ResurrectionEventHandler instance, Direction side) {
            return IntNBT.valueOf(instance.getTicks());
        }

        @Override
        public void readNBT(Capability<ResurrectionEventHandler> capability, ResurrectionEventHandler instance, Direction side, INBT nbt) {
            instance.setTicks(((IntNBT) nbt).getAsInt());
        }
    }
}
