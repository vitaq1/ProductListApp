package by.bsuir.vshu.productlistapp.presentation.home

import by.bsuir.vshu.productlistapp.common.Category
import by.bsuir.vshu.productlistapp.domain.model.Item

data class ItemListState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    var category: Category = Category.MEN,
    val error: String = ""
)
