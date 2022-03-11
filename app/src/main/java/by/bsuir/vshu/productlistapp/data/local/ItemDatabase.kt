package by.bsuir.vshu.productlistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import by.bsuir.vshu.productlistapp.data.local.dao.ItemDao


@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class ItemDatabase: RoomDatabase() {

        abstract val dao: ItemDao
}