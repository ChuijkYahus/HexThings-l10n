package io.github.real_septicake.hexthings;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.HexRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class HexthingsTags {
    public static final class Iota {
        public static final TagKey<IotaType<?>> DICT_KEY_BLACKLIST = TagKey.create(
                HexRegistries.IOTA_TYPE,
                new ResourceLocation(Hexthings.MODID, "dict_key_blacklist"));
    }
}
