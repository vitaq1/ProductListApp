package by.bsuir.vshu.productlistapp.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.bsuir.vshu.productlistapp.data.remote.dto.Rating
import by.bsuir.vshu.productlistapp.domain.model.Item

@Entity
data class ItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val category: String,
    val description: String,
    @Embedded val rating: Rating,
    val image: String,
    val price: Double,
) {
    fun toItem(): Item {
        return Item(
            id = id,
            title = title,
            category = category,
            description = description,
            rating = rating,
            image = image,
            price = price
        )
    }
}