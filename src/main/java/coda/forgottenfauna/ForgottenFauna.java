package coda.forgottenfauna;

import coda.forgottenfauna.entities.BaijiEntity;
import coda.forgottenfauna.entities.DodoEntity;
import coda.forgottenfauna.entities.ThylacineEntity;
import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.client.ClientEvents;
import coda.forgottenfauna.init.FFSounds;
import coda.forgottenfauna.world.ResurrectionEventCapability;
import coda.forgottenfauna.world.ResurrectionEventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

@Mod(ForgottenFauna.MOD_ID)
public class ForgottenFauna {
    public static final String MOD_ID = "forgottenfauna";

    public ForgottenFauna() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::registerClient);
        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerBiomes);
        forgeBus.addGenericListener(World.class, this::attachCapabilities);
        forgeBus.addListener(this::worldTick);
        forgeBus.addListener(this::entityJoinWorld);

        FFItems.REGISTRY.register(bus);
        FFEntities.REGISTRY.register(bus);
        FFSounds.REGISTRY.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ResurrectionEventHandler.class, new ResurrectionEventCapability.Storage(), ResurrectionEventHandler::new);
    }

    private void registerBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.SAVANNA) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(FFEntities.THYLACINE.get(), 500, 1, 2));
        }
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FFEntities.THYLACINE.get(), ThylacineEntity.createAttributes().create());
        event.put(FFEntities.BAIJI.get(), BaijiEntity.createAttributes().create());
        event.put(FFEntities.DODO.get(), DodoEntity.createAttributes().create());
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
            if (handler.isActive()) {
                event.setCanceled(true);
            }
        });
    }

    // TODO make a proper way to start a resurrection
/*    private void startResurrection(BlockEvent.BreakEvent event) {
        final ResurrectionEventHandler handler = new ResurrectionEventHandler();
        if (event.getState().getBlock() instanceof CoralBlock) {
            handler.start(1000);
        }
    }*/

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(FFItems.THYLACINE_SPAWN_EGG.get());
        }
    };
}