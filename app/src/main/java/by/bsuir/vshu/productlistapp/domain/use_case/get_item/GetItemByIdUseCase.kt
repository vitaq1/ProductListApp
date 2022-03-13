package by.bsuir.vshu.productlistapp.domain.use_case.get_item

import by.bsuir.vshu.productlistapp.util.Resource
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemByIdUseCase  @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Item>> {
        return repository.getItemById(id)
    }

}