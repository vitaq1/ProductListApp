package by.bsuir.vshu.productlistapp.data.repository

import by.bsuir.vshu.productlistapp.data.local.dao.ItemDao
import by.bsuir.vshu.productlistapp.data.remote.StoreApi
import by.bsuir.vshu.productlistapp.data.remote.dto.toItemEntity
import by.bsuir.vshu.productlistapp.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import by.bsuir.vshu.productlistapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: StoreApi,
    private val dao: ItemDao
) : ItemRepository {

    override fun getItems(): Flow<Resource<List<Item>>> = flow {

        emit(Resource.Loading<List<Item>>())

        val items = dao.getItems().map { it.toItem() }
        emit(Resource.Loading(data = items))
        try {
            val remoteItems = api.getItems()
            dao.deleteItems()
            dao.insertItems(remoteItems.map { it.toItemEntity() })
            println("loaded from api")
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = items
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = items
                )
            )
        }

        val newItems = dao.getItems().map { it.toItem() }
        emit(Resource.Success(newItems))
    }

    override fun getItemById(id: String): Flow<Resource<Item>> = flow {

        try {
            emit(Resource.Loading<Item>())
            val item = dao.getItemById(id).toItem()
            emit(Resource.Success<Item>(item))
        } catch (e: IOException) {
            emit(Resource.Error<Item>("Unexpected error"))
        }
    }

    override suspend fun updateItem(item: Item) {

        dao.updateItem(item.toItemEntity())

    }

    override suspend fun getCurrencies(): List<Double> {
        return api.getCurrencies()
    }
}
