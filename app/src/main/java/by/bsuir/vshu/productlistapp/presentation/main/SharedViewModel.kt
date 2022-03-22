package by.bsuir.vshu.productlistapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.vshu.productlistapp.domain.use_case.get_currencies.GetCurrenciesUseCase
import by.bsuir.vshu.productlistapp.util.Category
import by.bsuir.vshu.productlistapp.util.Resource
import by.bsuir.vshu.productlistapp.domain.use_case.get_items.GetItemsUseCase
import by.bsuir.vshu.productlistapp.util.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    var itemListState: MutableLiveData<ItemListState> = MutableLiveData()

    init {
        updateItems()
        updateCurrencies()
    }


    fun updateItems() {
        getItemsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    itemListState.value = ItemListState(items = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    itemListState.value = ItemListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    itemListState.value = ItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getItemCountByCategory(category: Category): Int{
        var mCount = 0
        var wCount = 0
        for (item in itemListState.value?.items!!){
            if (item.category == Category.SHOES.s) mCount++
            else wCount++
        }

        if (category == Category.SHOES) return mCount
        return wCount
    }



    private fun updateCurrencies(){
        viewModelScope.launch {
            val currencies = getCurrenciesUseCase()
            Currency.EUR.coeff = currencies[0]
            Currency.USD.coeff = currencies[1]
            Currency.RUB.coeff = currencies[2] / 100
            Currency.GBP.coeff = currencies[3]

        }
    }

}

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}
