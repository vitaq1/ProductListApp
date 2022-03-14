package by.bsuir.vshu.productlistapp.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.util.Category
import by.bsuir.vshu.productlistapp.domain.model.Item
import com.bumptech.glide.Glide

class ItemAdapter(private val items: List<Item>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: View
        val category: Category
        val itemImage: ImageView
        val itemBrand: TextView
        val itemName: TextView
        val itemPrice: TextView


        init {
            category = Category.SHOES
            itemBrand = view.findViewById(R.id.itemListBrandText)
            itemImage = view.findViewById(R.id.itemListImageView)
            itemName = view.findViewById(R.id.itemListNameText)
            itemPrice = view.findViewById(R.id.itemListPriceText)
            context = view

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_view, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val currentItem: Item = items[position]
        Glide.with(viewHolder.context)
            .load(currentItem.image)
            .into(viewHolder.itemImage);
        viewHolder.itemBrand.text = currentItem.brand
        viewHolder.itemName.text = currentItem.name
        viewHolder.itemPrice.text = currentItem.price.toString()

        viewHolder.context.setOnClickListener { listener.onItemClick(viewHolder.context, items[position].id) }
    }



    override fun getItemCount() = items.size

}