package by.bsuir.vshu.productlistapp.domain.use_case.update_item

import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import by.bsuir.vshu.productlistapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {

    suspend operator fun invoke(item: Item)  {
        repository.updateItem(item)
    }

}