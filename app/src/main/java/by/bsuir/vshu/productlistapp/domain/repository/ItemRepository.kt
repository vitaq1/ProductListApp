package by.bsuir.vshu.productlistapp.domain.repository

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto

interface ItemRepository {

    suspend fun getItems(): List<ItemDto>

    suspend fun getItemById(id: String): ItemDto

}