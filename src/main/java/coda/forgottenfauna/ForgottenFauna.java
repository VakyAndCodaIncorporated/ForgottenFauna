package coda.forgottenfauna;

import coda.forgottenfauna.entities.BaijiEntity;
import coda.forgottenfauna.entities.ThylacineEntity;
import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.init.FFItems;
import coda.forgottenfauna.client.ClientEvents;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

        FFItems.REGISTRY.register(bus);
        FFEntities.REGISTRY.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(FFEntities.THYLACINE.get(), ThylacineEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(FFEntities.BAIJI.get(), BaijiEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
         ClientEvents.init();
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(FFItems.THYLACINE_SPAWN_EGG.get());
        }
    };
}