package coda.forgottenfauna;

import coda.forgottenfauna.entities.BaijiEntity;
import coda.forgottenfauna.entities.ThylacineEntity;
import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.client.ClientEvents;
import coda.forgottenfauna.world.ResurrectionEventCapability;
import coda.forgottenfauna.world.ResurrectionEventHandler;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ForgottenFauna.MOD_ID)
public class ForgottenFauna {
    public static final String MOD_ID = "forgottenfauna";

    public ForgottenFauna() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::registerClient);
        bus.addListener(this::registerCommon);
        MinecraftForge.EVENT_BUS.addGenericListener(World.class, this::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::worldTick);
        MinecraftForge.EVENT_BUS.addListener(this::entityJoinWorld);

        FFItems.REGISTRY.register(bus);
        FFEntities.REGISTRY.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        CapabilityManager.INSTANCE.register(ResurrectionEventHandler.class, new ResurrectionEventCapability.Storage(), ResurrectionEventHandler::new);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(FFEntities.THYLACINE.get(), ThylacineEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(FFEntities.BAIJI.get(), BaijiEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
         ClientEvents.init();
    }

    private void attachCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(ResurrectionEventCapability.ID, new ResurrectionEventCapability());
    }

    private void worldTick(TickEvent.WorldTickEvent event) {
        event.world.getCapability(ResurrectionEventCapability.capability).ifPresent(ResurrectionEventHandler::tick);
    }

    private void entityJoinWorld(EntityJoinWorldEvent event) {
        event.getWorld().getCapability(ResurrectionEventCapability.capability).ifPresent(handler -> {
            //TODO for Coda to implement
        });
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(FFItems.THYLACINE_SPAWN_EGG.get());
        }
    };
}
