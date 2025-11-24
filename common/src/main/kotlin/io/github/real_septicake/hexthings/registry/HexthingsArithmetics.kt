@file:Suppress("unused")

package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexArithmetics
import io.github.real_septicake.hexthings.casting.arithmetic.DictArithmetic
import io.github.real_septicake.hexthings.casting.arithmetic.ListArithmetic

object HexthingsArithmetics : HexthingsRegistrar<Arithmetic>(
    HexRegistries.ARITHMETIC,
    { HexArithmetics.REGISTRY }
) {
    val DICT_ARITH = make("dict_arith", DictArithmetic)
    val UIUA_ARITH = make("uiua_arith", ListArithmetic)

    private fun make(name: String, arithmetic: Arithmetic) = register(name) { arithmetic }
}