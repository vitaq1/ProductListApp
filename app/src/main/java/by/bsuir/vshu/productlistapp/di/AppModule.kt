package by.bsuir.vshu.productlistapp.di

import android.app.Application
import androidx.room.Room
import by.bsuir.vshu.productlistapp.data.local.ItemDatabase
import by.bsuir.vshu.productlistapp.data.local.dao.ItemDao
import by.bsuir.vshu.productlistapp.util.Constants
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
    fun provideItemRepository(api: FakeStoreAPI, db: ItemDatabase): ItemRepository {
        return ItemRepositoryImpl(api, db.dao)
    }


    @Provides
    @Singleton
    fun provideItemDatabase(app: Application): ItemDatabase {
        return Room.databaseBuilder(
            app, ItemDatabase::class.java, "item_db"
        ).build()
    }
}