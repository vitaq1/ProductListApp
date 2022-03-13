package by.bsuir.vshu.productlistapp.domain.model

data class Item(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val size: String,
    val image: String,
    val price: Double,
    var comment: String,
)
