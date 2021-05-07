package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.items.ForgottenFaunaSpawnEggItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ForgottenFauna.MOD_ID);

    // Spawn Eggs
    public static final RegistryObject<Item> THYLACINE_SPAWN_EGG = REGISTRY.register("thylacine_spawn_egg", () -> new ForgottenFaunaSpawnEggItem(FFEntities.THYLACINE, 0xdda15e, 0x4a2a26, new Item.Properties().group(ForgottenFauna.GROUP)));
    public static final RegistryObject<Item> BAIJI_SPAWN_EGG = REGISTRY.register("baiji_spawn_egg", () -> new ForgottenFaunaSpawnEggItem(FFEntities.BAIJI, 0x7c8a9a, 0xd5d4cd, new Item.Properties().group(ForgottenFauna.GROUP)));
}