package by.bsuir.vshu.productlistapp.data.remote.parser

import by.bsuir.vshu.productlistapp.data.remote.dto.ItemDto
import by.bsuir.vshu.productlistapp.util.Constants
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.function.Consumer

class WebParser {

    suspend fun getItems(): List<ItemDto> {
        val itemList: MutableList<ItemDto> = mutableListOf()

        itemList.addAll(parse(Constants.SHOES_URL))
        itemList.addAll(parse(Constants.ACCESSORIES_URL))

        println("Amount of extracted products " + itemList.size)
        return itemList
    }

    suspend fun parse(url: String): List<ItemDto> {
        val items: MutableList<ItemDto> = ArrayList<ItemDto>()
        val doc = Jsoup.connect(url)
            .referrer("http://www.google.com")
            .get()
        val category = doc.getElementsByAttributeValueContaining(
            "data-testid",
            "categoryHeaderTitle"
        )[0].textNodes()[0].text()
        val elements = doc.getElementsByAttributeValueContaining("style", "product-tile")
        println("found " + elements.size + " elems")
        println("category: $category")
        elements.forEach(Consumer { element: Element ->
            val id = getId(element)
            val brand = getBrand(element)
            val name = getName(element, brand)
            val price = getPrice(element)
            val size = getSize(element)
            val image = getImage(element)
            items.add(ItemDto(id, name, brand, category, size, image, price))
        })
        return items
    }

    private fun getId(element: Element): String {
        return element.select("a").attr("data-test-id", "ProductTile")
            .attr("href")
    }

    private fun getBrand(element: Element): String {
        return element.select("p").attr("data-test-id", "BrandName").text()
    }

    private fun getPrice(element: Element): Double {
        return element.getElementsByAttributeValueContaining("data-testid", "finalPrice").text()
            .filter { !it.isLetter() }.replace(",", ".").toDouble()
    }

    private fun getSize(element: Element): String {
        return element.getElementsByAttributeValueContaining("data-testid", "Sizes").text()
    }

    private fun getImage(element: Element): String {
        return element.getElementsByAttributeValueContaining("data-testid", "productImage")
            .attr("srcset").split(" ").toTypedArray()[2]
    }

    private fun getName(element: Element, brand: String): String {
        val temp = element.getElementsByAttributeValueContaining("data-testid", "productImage")
            .attr("alt").toString().replace(brand, "").replaceFirst(" ","")
        return temp.substring(0, temp.length - 7)
    }

}