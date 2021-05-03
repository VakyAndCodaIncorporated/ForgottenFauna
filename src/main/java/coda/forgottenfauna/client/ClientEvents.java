package coda.forgottenfauna.client;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.client.renderer.BaijiRenderer;
import coda.forgottenfauna.client.renderer.ThylacineRenderer;
import coda.forgottenfauna.init.FFEntities;
import coda.forgottenfauna.items.ForgottenFaunaSpawnEggItem;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ForgottenFauna.MOD_ID)
public class ClientEvents {

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(FFEntities.THYLACINE.get(), ThylacineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FFEntities.BAIJI.get(), BaijiRenderer::new);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((ForgottenFaunaSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (ForgottenFaunaSpawnEggItem e : ForgottenFaunaSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}
