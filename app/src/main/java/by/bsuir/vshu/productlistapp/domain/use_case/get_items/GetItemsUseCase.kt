package by.bsuir.vshu.productlistapp.domain.use_case.get_items

import by.bsuir.vshu.productlistapp.common.Resource
import by.bsuir.vshu.productlistapp.data.remote.dto.toItem
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
){

    operator fun invoke(): Flow<Resource<List<Item>>> = flow {
        try {
            emit(Resource.Loading<List<Item>>())
            val items = repository.getItems().map { it.toItem() }
            emit(Resource.Success<List<Item>>(items))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Item>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Item>>("Couldn't reach server. Check your internet connection."))
        }
    }

}