package coda.forgottenfauna.init;

import coda.forgottenfauna.ForgottenFauna;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.*;

public class FFTags {
    public static final ITag.INamedTag<EntityType<?>> RESURRECTED_ENTITY = entityTypeTag("resurrected");

    private static ITag.INamedTag<EntityType<?>> entityTypeTag(String path) {
        return EntityTypeTags.bind(ForgottenFauna.MOD_ID + ":" + path);
    }
}
