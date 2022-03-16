package by.bsuir.vshu.productlistapp.domain.repository

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getItems(): Flow<Resource<List<Item>>>

    fun getItemById(id: String): Flow<Resource<Item>>

    suspend fun updateItem(item: Item)
}