package by.bsuir.vshu.productlistapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.bsuir.vshu.productlistapp.domain.model.Result

@Entity
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val desc: String
)
{
    fun toResult(): Result {
        return Result(
            id = id,
            date = date,
            desc = desc
        )
    }
}
