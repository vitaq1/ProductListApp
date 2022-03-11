package by.bsuir.vshu.productlistapp.presentation.home

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

class ItemAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: View
        val category: Category
        val itemImage: ImageView
        val itemTitle: TextView
        val itemRating: TextView
        val itemPrice: TextView


        init {
            category = Category.MEN
            itemTitle = view.findViewById(R.id.itemListTitleText)
            itemImage = view.findViewById(R.id.itemListImageView)
            itemRating = view.findViewById(R.id.itemListRatingText)
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
        viewHolder.itemTitle.text = currentItem.title
        viewHolder.itemRating.text = currentItem.rating.rate.toString()
        viewHolder.itemPrice.text = currentItem.price.toString()
    }

    override fun getItemCount() = items.size

}