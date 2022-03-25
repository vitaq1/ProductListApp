package by.bsuir.vshu.productlistapp.domain.model

import by.bsuir.vshu.productlistapp.data.local.ItemEntity
import by.bsuir.vshu.productlistapp.data.local.ResultEntity


data class Result(

    val id: Int = 0,
    val date: String,
    val desc: String

){
    fun toResultEntity(): ResultEntity {
        return ResultEntity(
            id = id,
            date = date,
            desc = desc
        )
    }
}
