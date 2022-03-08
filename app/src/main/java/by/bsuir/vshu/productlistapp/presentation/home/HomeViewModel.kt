package by.bsuir.vshu.productlistapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.vshu.productlistapp.common.Resource
import by.bsuir.vshu.productlistapp.domain.use_case.get_items.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private lateinit var itemListState: MutableLiveData<ItemListState>

    init {
        getItems()
    }

    private fun getItems(){
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

}