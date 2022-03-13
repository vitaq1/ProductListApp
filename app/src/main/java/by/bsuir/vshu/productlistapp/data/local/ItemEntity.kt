package by.bsuir.vshu.productlistapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.bsuir.vshu.productlistapp.domain.model.Item

@Entity
data class ItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val size: String,
    val image: String,
    val price: Double,
    var comment: String = "",
) {
    fun toItem(): Item {
        return Item(
            id = id,
            name = name,
            brand = brand,
            category = category,
            size = size,
            image = image,
            price = price,
            comment = comment
        )
    }
}