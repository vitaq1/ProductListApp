package by.bsuir.vshu.productlistapp.domain.use_case.get_currencies

import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: ItemRepository
) {

    suspend operator fun invoke(): List<Double>  {
        return repository.getCurrencies()
    }

}