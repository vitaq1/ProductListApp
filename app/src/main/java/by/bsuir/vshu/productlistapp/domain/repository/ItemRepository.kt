package by.bsuir.vshu.productlistapp.domain.repository

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.model.Result
import by.bsuir.vshu.productlistapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getItems(): Flow<Resource<List<Item>>>

    fun getItemById(id: String): Flow<Resource<Item>>

    suspend fun getCurrencies(): List<Double>

    suspend fun updateItem(item: Item)

    fun getResults(): Flow<Resource<List<Result>>>

    suspend fun insertResult(result: Result)

}