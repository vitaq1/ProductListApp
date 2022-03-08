package by.bsuir.vshu.productlistapp.di

import by.bsuir.vshu.productlistapp.common.Constants
import by.bsuir.vshu.productlistapp.data.remote.FakeStoreAPI
import by.bsuir.vshu.productlistapp.data.repository.ItemRepositoryImpl
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStoreApi(): FakeStoreAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeStoreAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideItemRepository(api: FakeStoreAPI): ItemRepository {
        return ItemRepositoryImpl(api)
    }
}