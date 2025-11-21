package io.github.real_septicake.hexthings.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.GarbageIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import net.minecraft.world.item.DyeColor

class MishapInvalidKeyType(private val type: IotaType<*>) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context) = dyeColor(DyeColor.BROWN)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) = error("invalid_key_type", type.typeName())

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        stack.add(GarbageIota())
    }
}