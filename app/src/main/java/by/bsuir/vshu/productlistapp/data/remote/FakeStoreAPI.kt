package by.bsuir.vshu.productlistapp.data.remote

import retrofit2.http.GET

interface FakeStoreAPI {

    @GET("/products")
    suspend fun getItems()
    

}