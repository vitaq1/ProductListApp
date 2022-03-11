package by.bsuir.vshu.productlistapp.util

enum class Category(val s: String) {
    MEN("men's clothing") {
        override fun getComplementCategory(): String {
            return "electronics"
        }
    },
    WOMEN("women's clothing") {
        override fun getComplementCategory(): String {
            return "jewelery"
        }
    };


    abstract fun getComplementCategory(): String

}