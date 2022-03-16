package by.bsuir.vshu.productlistapp.data.local.dao

import androidx.room.*
import by.bsuir.vshu.productlistapp.data.local.ItemEntity
import by.bsuir.vshu.productlistapp.domain.model.Item
import retrofit2.http.DELETE
import retrofit2.http.GET

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Query("DELETE FROM itementity")
    suspend fun deleteItems()

    @Query("SELECT * FROM itementity")
    suspend fun getItems(): List<ItemEntity>

    @Query("SELECT * FROM itementity WHERE id=:id ")
    suspend fun getItemById(id: String): ItemEntity

    @Update
    suspend fun updateItem(item: ItemEntity)
}