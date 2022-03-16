package by.bsuir.vshu.productlistapp.domain.model

import by.bsuir.vshu.productlistapp.data.local.ItemEntity

data class Item(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val size: String,
    val image: String,
    val price: Double,
    var comment: String,
    var isFavorite: Boolean
) {
    fun toItemEntity(): ItemEntity {
        return ItemEntity(
            id = id,
            name = name,
            brand = brand,
            category = category,
            size = size,
            image = image,
            price = price,
            comment = comment,
            isFavorite = isFavorite
        )
    }
}