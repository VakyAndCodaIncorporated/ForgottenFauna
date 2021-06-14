package coda.forgottenfauna.common.items;

import coda.weecore.WeeCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class FFSpawnEggItem<T extends Entity> extends SpawnEggItem {
    public static final Set<FFSpawnEggItem<?>> SPAWN_EGGS = new HashSet<>();
    public final Lazy<EntityType<T>> type;

    @SuppressWarnings("ConstantConditions")
    public FFSpawnEggItem(Supplier<EntityType<T>> type, int primaryColor, int secondaryColor, Properties properties) {
        super(null, primaryColor, secondaryColor, properties);

        this.type = Lazy.of(type);
        SPAWN_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT tag) {
        if (tag != null && tag.contains("EntityTag", 10)) {
            CompoundNBT childTag = tag.getCompound("EntityTag");
            if (childTag.contains("id", 8))
                return EntityType.byString(childTag.getString("id")).orElse(type.get());
        }

        return type.get();
    }

    public static void addEggsToMap() {
        try {
            Map<EntityType<?>, SpawnEggItem> eggMap = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
            for (FFSpawnEggItem<?> item : SPAWN_EGGS) eggMap.put(item.type.get(), item);
        }
        catch (Exception e) {
            WeeCore.LOGGER.fatal("Unable to add spawn eggs to SpawnEgg map", e);
        }
    }
}