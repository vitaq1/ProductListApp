package by.bsuir.vshu.productlistapp.di

import android.app.Application
import androidx.room.Room
import by.bsuir.vshu.productlistapp.data.local.ItemDatabase
import by.bsuir.vshu.productlistapp.data.remote.StoreApi
import by.bsuir.vshu.productlistapp.data.remote.parser.WebParser
import by.bsuir.vshu.productlistapp.data.repository.ItemRepositoryImpl
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStoreApi(parser: WebParser): StoreApi {
        return StoreApi(parser)
    }

    @Provides
    @Singleton
    fun provideWebParser(): WebParser {
        return WebParser()
    }

    @Provides
    @Singleton
    fun provideItemRepository(api: StoreApi, db: ItemDatabase): ItemRepository {
        return ItemRepositoryImpl(api, db.dao)
    }


    @Provides
    @Singleton
    fun provideItemDatabase(app: Application): ItemDatabase {
        return Room.databaseBuilder(
            app, ItemDatabase::class.java, "item_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}