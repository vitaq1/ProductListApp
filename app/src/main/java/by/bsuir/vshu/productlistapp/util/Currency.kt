package by.bsuir.vshu.productlistapp.util

enum class Currency(public val sign: String, var coeff: Double) {

    EUR("€",1.0),
    USD("$",1.0),
    RUB("₽",1.0),
    GBP("£",1.0)

}