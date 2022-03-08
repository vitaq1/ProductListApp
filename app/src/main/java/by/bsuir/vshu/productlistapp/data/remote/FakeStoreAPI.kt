package by.bsuir.vshu.productlistapp.data.remote

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreAPI {

    @GET("/products")
    suspend fun getItems(): List<ItemDto>

    @GET("/products/{itemId}")
    suspend fun getItemById(@Path("itemId") itemId: String): ItemDto
}