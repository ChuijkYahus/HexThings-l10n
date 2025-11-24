package io.github.real_septicake.hexthings.casting.actions.dict

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.ListIota
import io.github.real_septicake.hexthings.getDict
import net.minecraft.nbt.TagParser.parseTag

object OpDictEntries : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val map = args.getDict(0, argc)
        val list = mutableListOf<Iota>()
        for(entry in map.map) {
            list += ListIota(listOf(IotaType.deserialize(parseTag(entry.key), env.world), entry.value))
        }
        return listOf(ListIota(list))
    }
}