package io.github.real_septicake.hexthings.casting.actions.list.uiua

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota

object OpUiuaRotate : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val l = args.getList(0, argc)
        val list = l.toList()
        val shift = args.getPositiveInt(1, argc) % list.size
        if(shift == 0)
            return listOf(ListIota(list))
        val shiftInv = list.size - shift
        val front = list.subList(shiftInv, list.size)
        val back = list.subList(0, shiftInv)
        val ret = front.toMutableList()
        ret.addAll(back)
        return listOf(ListIota(ret))
    }
}