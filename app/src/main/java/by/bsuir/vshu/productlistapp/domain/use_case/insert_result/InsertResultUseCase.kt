package by.bsuir.vshu.productlistapp.domain.use_case.insert_result

import by.bsuir.vshu.productlistapp.domain.model.Result
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InsertResultUseCase @Inject constructor(
    private val repository: ItemRepository
) {

    suspend operator fun invoke(description: String) {
        repository.insertResult(
            Result(
                desc = description, date = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(
                    Calendar.getInstance().time
                ).toString()
            )
        )
    }

}