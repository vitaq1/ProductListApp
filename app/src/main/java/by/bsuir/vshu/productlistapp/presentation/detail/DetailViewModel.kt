package by.bsuir.vshu.productlistapp.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.vshu.productlistapp.domain.model.Item
import by.bsuir.vshu.productlistapp.domain.use_case.get_item.GetItemByIdUseCase
import by.bsuir.vshu.productlistapp.domain.use_case.get_items.GetItemsUseCase
import by.bsuir.vshu.productlistapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    var item: MutableLiveData<Item> = MutableLiveData()


    init {

    }

    fun loadItemById(id: String) {
        getItemByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    item.value = result.data!!
                    println(item.value)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

}