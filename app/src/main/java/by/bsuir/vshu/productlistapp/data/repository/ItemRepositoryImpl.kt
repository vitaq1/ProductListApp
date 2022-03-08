package by.bsuir.vshu.productlistapp.data.repository

import by.bsuir.vshu.productlistapp.data.remote.FakeStoreAPI
import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: FakeStoreAPI
):ItemRepository {

    override suspend fun getItems(): List<ItemDto> {
        return api.getItems()
    }

    override suspend fun getItemById(id: String): ItemDto {
        return api.getItemById(id)
    }
}