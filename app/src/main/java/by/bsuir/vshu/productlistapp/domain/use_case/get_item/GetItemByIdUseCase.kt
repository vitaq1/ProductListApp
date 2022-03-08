package by.bsuir.vshu.productlistapp.domain.use_case.get_item

import by.bsuir.vshu.productlistapp.common.Resource
import by.bsuir.vshu.productlistapp.data.remote.dto.toItem
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetItemByIdUseCase  @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(itemId: String): Flow<Resource<Item>> = flow {
        try {
            emit(Resource.Loading<Item>())
            val item = repository.getItemById(itemId).toItem()
            emit(Resource.Success<Item>(item))
        } catch(e: HttpException) {
            emit(Resource.Error<Item>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<Item>("Couldn't reach server. Check your internet connection."))
        }
    }
}