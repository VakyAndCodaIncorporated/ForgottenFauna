package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ForgottenFauna.MOD_ID);

    public static final RegistryObject<SoundEvent> THYLACINE_HURT = REGISTRY.register("thylacine_hurt", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "thylacine.hurt")));
    public static final RegistryObject<SoundEvent> THYLACINE_AMBIENT = REGISTRY.register("thylacine_ambient", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "thylacine.ambient")));
    public static final RegistryObject<SoundEvent> THYLACINE_DEATH = REGISTRY.register("thylacine_death", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "thylacine.death")));
    public static final RegistryObject<SoundEvent> THYLACINE_ATTACK = REGISTRY.register("thylacine_attack", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "thylacine.attack")));
    public static final RegistryObject<SoundEvent> DODO_HURT = REGISTRY.register("dodo_hurt", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "dodo.hurt")));
    public static final RegistryObject<SoundEvent> DODO_AMBIENT = REGISTRY.register("dodo_ambient", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "dodo.ambient")));
    public static final RegistryObject<SoundEvent> DODO_DEATH = REGISTRY.register("dodo_death", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "dodo.death")));
    public static final RegistryObject<SoundEvent> STELLERS_SEA_COW_HURT = REGISTRY.register("stellers_sea_cow_hurt", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "stellers_sea_cow.hurt")));
    public static final RegistryObject<SoundEvent> STELLERS_SEA_COW_DEATH = REGISTRY.register("stellers_sea_cow_death", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "stellers_sea_cow.death")));
    public static final RegistryObject<SoundEvent> GREAT_AUK_HURT = REGISTRY.register("great_auk_hurt", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "great_auk.hurt")));
    public static final RegistryObject<SoundEvent> GREAT_AUK_AMBIENT = REGISTRY.register("great_auk_ambient", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "great_auk.ambient")));
    public static final RegistryObject<SoundEvent> GREAT_AUK_DEATH = REGISTRY.register("great_auk_death", () -> new SoundEvent(new ResourceLocation(ForgottenFauna.MOD_ID, "great_auk.death")));
}
