package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.common.items.FFSpawnEggItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ForgottenFauna.MOD_ID);

    // Spawn Eggs
    public static final RegistryObject<Item> THYLACINE_SPAWN_EGG = REGISTRY.register("thylacine_spawn_egg", () -> new FFSpawnEggItem<>(FFEntities.THYLACINE, 0xdda15e, 0x4a2a26, new Item.Properties().tab(ForgottenFauna.GROUP)));
    public static final RegistryObject<Item> BAIJI_SPAWN_EGG = REGISTRY.register("baiji_spawn_egg", () -> new FFSpawnEggItem<>(FFEntities.BAIJI, 0x63636e, 0x98a7a0, new Item.Properties().tab(ForgottenFauna.GROUP)));
    public static final RegistryObject<Item> DODO_SPAWN_EGG = REGISTRY.register("dodo_spawn_egg", () -> new FFSpawnEggItem<>(FFEntities.DODO, 0x473e3e, 0x847f67, new Item.Properties().tab(ForgottenFauna.GROUP)));
    public static final RegistryObject<Item> STELLERS_SEA_COW_SPAWN_EGG = REGISTRY.register("stellers_sea_cow_spawn_egg", () -> new FFSpawnEggItem<>(FFEntities.STELLERS_SEA_COW, 0x625753, 0x90856f, new Item.Properties().tab(ForgottenFauna.GROUP)));
    public static final RegistryObject<Item> GREAT_AUK_SPAWN_EGG = REGISTRY.register("great_auk_spawn_egg", () -> new FFSpawnEggItem<>(FFEntities.GREAT_AUK, 0x413a49, 0xd3cebd, new Item.Properties().tab(ForgottenFauna.GROUP)));
}
