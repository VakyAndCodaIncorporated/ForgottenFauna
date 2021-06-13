package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import coda.forgottenfauna.common.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, ForgottenFauna.MOD_ID);

    public static final RegistryObject<EntityType<ThylacineEntity>> THYLACINE = create("thylacine", EntityType.Builder.of(ThylacineEntity::new, EntityClassification.CREATURE).sized(0.65f, 0.65f));
    public static final RegistryObject<EntityType<BaijiEntity>> BAIJI = create("baiji", EntityType.Builder.of(BaijiEntity::new, EntityClassification.WATER_CREATURE).sized(0.85f, 0.55f));
    public static final RegistryObject<EntityType<DodoEntity>> DODO = create("dodo", EntityType.Builder.of(DodoEntity::new, EntityClassification.CREATURE).sized(0.85f, 1.0f));
    public static final RegistryObject<EntityType<StellersSeaCowEntity>> STELLERS_SEA_COW = create("stellers_sea_cow", EntityType.Builder.of(StellersSeaCowEntity::new, EntityClassification.WATER_CREATURE).sized(2.75f, 1.85f));
    public static final RegistryObject<EntityType<GreatAukEntity>> GREAT_AUK = create("great_auk", EntityType.Builder.of(GreatAukEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.1f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(ForgottenFauna.MOD_ID + "." + name));
    }
}
