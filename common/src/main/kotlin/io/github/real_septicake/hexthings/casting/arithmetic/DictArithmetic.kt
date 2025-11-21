package io.github.real_septicake.hexthings.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.isOfTag
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import io.github.real_septicake.hexthings.HexthingsTags
import io.github.real_septicake.hexthings.casting.iota.DictIota
import io.github.real_septicake.hexthings.casting.mishaps.MishapInvalidKeyType

object DictArithmetic : Arithmetic {
    val impls = HashMap<HexPattern, Operator>()

    init {
        impls[Arithmetic.REPLACE] = mapKeyValToIota { dictIota, iota, iota2 ->
            val key = HexIotaTypes.REGISTRY.getKey(iota.type)!!
            if(isOfTag(HexIotaTypes.REGISTRY, key, HexthingsTags.Iota.DICT_KEY_BLACKLIST))
                throw MishapInvalidKeyType(iota.type)
            if(iota2.type != HexIotaTypes.NULL)
                dictIota[iota] = iota2
            else
                dictIota.map.remove(IotaType.serialize(iota).asString)
            dictIota
        }
        impls[Arithmetic.REMOVE] = mapKeyToIotas { dictIota, iota -> listOf(dictIota, dictIota.map.remove(IotaType.serialize(iota).asString) ?: NullIota()) }
        impls[Arithmetic.INDEX_OF] = mapKeyToIotas { dictIota, iota -> listOf(dictIota[iota] ?: NullIota()) }
        impls[Arithmetic.ABS] = mapToIota { DoubleIota(it.map.size.toDouble()) }
    }

    override fun arithName() = "map_arithmetic"

    override fun opTypes() = impls.keys

    override fun getOperator(pattern: HexPattern?) = impls[pattern]

    private fun mapKeyValToIota(op: (DictIota, Iota, Iota) -> Iota) = object : OperatorBasic (
        3,
        IotaMultiPredicate.triple(IotaPredicate.ofType(DictIota.TYPE), IotaPredicate.TRUE, IotaPredicate.TRUE)
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return listOf(op(it.next() as DictIota, it.next(), it.next()))
        }
    }

    private fun mapKeyToIotas(op: (DictIota, Iota) -> Iterable<Iota>) = object : OperatorBasic (
        2,
        IotaMultiPredicate.pair(IotaPredicate.ofType(DictIota.TYPE), IotaPredicate.TRUE)
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return op(it.next() as DictIota, it.next())
        }
    }

    private fun mapToIota(op: (DictIota) -> Iota) = object : OperatorBasic(
        1,
        IotaMultiPredicate.all(IotaPredicate.ofType(DictIota.TYPE))
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return listOf(op(it.next() as DictIota))
        }
    }
}