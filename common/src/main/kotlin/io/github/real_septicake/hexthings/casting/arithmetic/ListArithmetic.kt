package io.github.real_septicake.hexthings.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.math.HexPattern

object ListArithmetic : Arithmetic {
    val impls = HashMap<HexPattern, Operator>()

    init {
        impls[Arithmetic.MUL] = twoListToIota { a, b -> ListIota(a.list.zip(b.list).map { ListIota(listOf(it.first, it.second)) }) }
        impls[Arithmetic.DIV] = twoListToIota { a, b ->
            val out = mutableListOf<Iota>()
            for(i_a in a.list) {
                for(i_b in b.list) {
                    out += ListIota(listOf(i_a, i_b))
                }
            }
            ListIota(out)
        }
    }

    override fun arithName() = "uiua"

    override fun opTypes() = impls.keys

    override fun getOperator(pattern: HexPattern?) = impls[pattern]

    fun twoListToIota(op: (ListIota, ListIota) -> Iota) = object : OperatorBasic(
        2, IotaMultiPredicate.all(IotaPredicate.ofType(ListIota.TYPE))
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return listOf(op(it.next() as ListIota, it.next() as ListIota))
        }
    }
}