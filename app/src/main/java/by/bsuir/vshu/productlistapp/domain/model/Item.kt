package by.bsuir.vshu.productlistapp.domain.model

import by.bsuir.vshu.productlistapp.data.remote.dto.Rating

data class Item(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val rating: Rating,
    val image: String,
    val price: Int,
)
