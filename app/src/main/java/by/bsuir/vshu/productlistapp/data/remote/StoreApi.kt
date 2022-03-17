package by.bsuir.vshu.productlistapp.data.remote

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.data.remote.parser.WebParser
import by.bsuir.vshu.productlistapp.util.Currency
import kotlinx.coroutines.*
import javax.inject.Inject


class StoreApi @Inject constructor(private val parser: WebParser) {

    suspend fun getItems(): List<ItemDto> {
        val items: List<ItemDto>
        withContext(Dispatchers.IO) {
            items = parser.getItems()
        }
        return items
    }

    suspend fun getCurrencies(): List<Double> {

        val currenciesDouble: List<Double>
        withContext(Dispatchers.IO) {
            currenciesDouble = parser.getCurrencies()
        }
        return currenciesDouble


    }


}