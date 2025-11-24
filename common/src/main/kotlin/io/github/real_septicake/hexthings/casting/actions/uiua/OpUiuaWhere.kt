package io.github.real_septicake.hexthings.casting.actions.uiua

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota

object OpUiuaWhere : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val l = args.getList(0, argc).toList()
        val list = mutableListOf<Iota>()
        l.forEachIndexed { index, iota -> if(iota.isTruthy) list += DoubleIota(index.toDouble())}
        return listOf(ListIota(list))
    }
}