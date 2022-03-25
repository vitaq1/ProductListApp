package by.bsuir.vshu.productlistapp.data.local.dao

import androidx.room.*
import by.bsuir.vshu.productlistapp.data.local.ItemEntity
import by.bsuir.vshu.productlistapp.data.local.ResultEntity
import by.bsuir.vshu.productlistapp.domain.model.Item
import retrofit2.http.DELETE
import retrofit2.http.GET

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: ItemEntity): Long

    @Query("DELETE FROM itementity")
    suspend fun deleteItems()

    @Query("SELECT * FROM itementity")
    suspend fun getItems(): List<ItemEntity>

    @Query("SELECT * FROM itementity WHERE id=:id ")
    suspend fun getItemById(id: String): ItemEntity

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Query("UPDATE itementity SET price = :price WHERE id = :id")
    suspend fun updateFields(id: String, price: Double)

    @Transaction
    suspend fun insertOrUpdateItems(items: List<ItemEntity>) {
        for (item in items) {
            val id = insertItem(item)
            if (id == -1L) {
                updateFields(item.id, item.price)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ResultEntity)

    @Query("SELECT * FROM resultentity")
    suspend fun getResults(): List<ResultEntity>

}