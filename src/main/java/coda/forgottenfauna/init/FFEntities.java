package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.entities.BaijiEntity;
import coda.forgottenfauna.entities.ThylacineEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, ForgottenFauna.MOD_ID);

    public static final RegistryObject<EntityType<ThylacineEntity>> THYLACINE = create("thylacine", EntityType.Builder.create(ThylacineEntity::new, EntityClassification.CREATURE).size(0.65f, 0.65f));
    public static final RegistryObject<EntityType<BaijiEntity>> BAIJI = create("baiji", EntityType.Builder.create(BaijiEntity::new, EntityClassification.WATER_CREATURE).size(0.85f, 0.55f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(ForgottenFauna.MOD_ID + "." + name));
    }
}