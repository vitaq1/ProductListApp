package by.bsuir.vshu.productlistapp.domain.use_case.get_results

import by.bsuir.vshu.productlistapp.domain.model.Result
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import by.bsuir.vshu.productlistapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetResultsUseCase @Inject constructor(
    private val repository: ItemRepository
) {

    operator fun invoke(): Flow<Resource<List<Result>>> {
        return repository.getResults()
    }

}