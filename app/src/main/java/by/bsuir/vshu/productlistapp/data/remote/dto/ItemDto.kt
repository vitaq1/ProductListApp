package by.bsuir.vshu.productlistapp.data.remote.dto

import by.bsuir.vshu.productlistapp.data.local.ItemEntity
import by.bsuir.vshu.productlistapp.domain.model.Item

data class ItemDto(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val size: String,
    val image: String,
    val price: Double,
)

fun ItemDto.toItemEntity(): ItemEntity {
    return ItemEntity(
        id = id,
        name = name,
        brand = brand,
        category = category,
        size = size,
        image = image,
        price = price,
    )
}