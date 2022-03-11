package by.bsuir.vshu.productlistapp.data.repository

import by.bsuir.vshu.productlistapp.data.local.dao.ItemDao
import by.bsuir.vshu.productlistapp.data.remote.FakeStoreAPI
import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.data.remote.dto.toItemEntity
import by.bsuir.vshu.productlistapp.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import by.bsuir.vshu.productlistapp.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

 class ItemRepositoryImpl @Inject constructor(
    private val api: FakeStoreAPI,
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

     override fun getItemById(id: Int): Flow<Resource<Item>> = flow {

         emit(Resource.Loading<Item>())

         val item = dao.getItemById(id).toItem()
         emit(Resource.Loading(data = item))

         try {
             val remoteItem = api.getItemById(id)
             dao.insertItem(remoteItem.toItemEntity())
         } catch (e: HttpException) {
             emit(
                 Resource.Error(
                     message = "Oops, something went wrong!",
                     data = item
                 )
             )
         } catch (e: IOException) {
             emit(
                 Resource.Error(
                     message = "Couldn't reach server, check your internet connection.",
                     data = item
                 )
             )
         }

         val newItem = dao.getItemById(id).toItem()
         emit(Resource.Success(newItem))
     }
 }
