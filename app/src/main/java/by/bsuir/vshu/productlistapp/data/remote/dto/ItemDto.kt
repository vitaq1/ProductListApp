package by.bsuir.vshu.productlistapp.data.remote.dto

import by.bsuir.vshu.productlistapp.domain.model.Item

data class ItemDto(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val rating: Rating,
    val image: String,
    val price: Double,
)

fun ItemDto.toItem(): Item {
    return Item(
        id = id,
        title = title,
        category = category,
        description = description,
        rating = rating,
        image = image,
        price = price
    )
}