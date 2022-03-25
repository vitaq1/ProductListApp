package by.bsuir.vshu.productlistapp.presentation.table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bsuir.vshu.productlistapp.domain.model.Result
import by.bsuir.vshu.productlistapp.domain.use_case.get_results.GetResultsUseCase
import by.bsuir.vshu.productlistapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val getResultsUseCase: GetResultsUseCase
) : ViewModel() {

    var results: MutableLiveData<List<Result>> = MutableLiveData()


    init {
        updateResults()
    }

    private fun updateResults(){
        getResultsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    results.value = result.data!!
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }

}