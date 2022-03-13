package by.bsuir.vshu.productlistapp.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.vshu.productlistapp.util.Category
import by.bsuir.vshu.productlistapp.util.Resource
import by.bsuir.vshu.productlistapp.domain.use_case.get_items.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    var itemListState: MutableLiveData<ItemListState> = MutableLiveData()

    init {
        loadItems()
    }

    private fun loadItems() {
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

    fun isInternetConnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}
