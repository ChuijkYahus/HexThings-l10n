package io.github.real_septicake.hexthings

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import io.github.real_septicake.hexthings.casting.iota.DictIota

fun List<Iota>.getDict(idx: Int, argc: Int = 0) : DictIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if(x !is DictIota)
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "map")
    return x
}