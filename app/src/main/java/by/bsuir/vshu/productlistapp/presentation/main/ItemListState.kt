package by.bsuir.vshu.productlistapp.presentation.main

import by.bsuir.vshu.productlistapp.util.Category
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.util.Currency

data class ItemListState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    var category: Category = Category.SHOES,
    var currency: Currency = Currency.EUR,
    val error: String = ""
)
