package by.bsuir.vshu.productlistapp.presentation.detail

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.main.SharedViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val model by viewModels<DetailViewModel>()

    //views
    private lateinit var detailImage: ImageView
    private lateinit var detailBrand: TextView
    private lateinit var detailName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViews()
        model.loadItemById(intent.extras!!.getString("itemId", ""))

        model.item.observe(this, Observer {
            Glide.with(this)
                .load(model.item.value?.image)
                .into(detailImage)
            detailBrand.text = model.item.value?.brand
            detailName.text = model.item.value?.name
        })
    }

    private fun initViews() {
        detailImage = findViewById(R.id.detailItemImage)
        detailBrand = findViewById(R.id.itemDetailBrandText)
        detailName = findViewById(R.id.itemDetailNameText)
    }
}