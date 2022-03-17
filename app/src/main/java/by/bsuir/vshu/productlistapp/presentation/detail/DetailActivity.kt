package by.bsuir.vshu.productlistapp.presentation.detail

import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.main.forceRefresh
import by.bsuir.vshu.productlistapp.util.Currency
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val model by viewModels<DetailViewModel>()

    //views
    private lateinit var detailImage: ImageView
    private lateinit var detailBrand: TextView
    private lateinit var detailName: TextView
    private lateinit var detailSize: TextView
    private lateinit var detailPrice: TextView
    private lateinit var detailCurrency: TextView
    private lateinit var backButton: ImageView
    private lateinit var goToWebButton: ImageView
    private lateinit var favoriteCheckBox: CheckBox
    private lateinit var addCommentButton: ImageView
    private lateinit var addCommentEditText: EditText
    private lateinit var commentText: Chip


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViews()
        model.loadItemById(intent.extras!!.getString("itemId", ""))
        model.currency.value = Currency.valueOf(intent.extras!!.getString("currency", "EUR"))
        setObserversToData()

        backButton.apply { setOnClickListener { super.onBackPressed() } }
        goToWebButton.apply { setOnClickListener { openWebView() } }
        addCommentButton.apply { setOnClickListener { addComment() } }
        commentText.apply {
            typeface = resources.getFont(R.font.gothic)
            setOnCloseIconClickListener(View.OnClickListener {
                commentText.visibility = View.GONE
                addCommentEditText.visibility = View.VISIBLE
                addCommentButton.visibility = View.VISIBLE
                model.item.value?.comment = ""
                model.updateItem()
            })
        }
        favoriteCheckBox.apply {
            setOnClickListener {
                model.item.value?.isFavorite = favoriteCheckBox.isChecked
                model.updateItem()
            }
        }


    }

    private fun initViews() {
        detailImage = findViewById(R.id.detailItemImage)
        detailBrand = findViewById(R.id.itemDetailBrandText)
        detailName = findViewById(R.id.itemDetailNameText)
        detailSize = findViewById(R.id.itemDetailSizeText)
        detailPrice = findViewById(R.id.itemDetailPriceText)
        detailCurrency = findViewById(R.id.itemDetailCurrencyText)
        backButton = findViewById(R.id.backButton)
        goToWebButton = findViewById(R.id.goToWebButton)
        favoriteCheckBox = findViewById(R.id.favoriteCheck)
        addCommentButton = findViewById(R.id.addCommentButton)
        addCommentEditText = findViewById(R.id.editText)
        commentText = findViewById(R.id.commentTextChip)
    }

    private fun setObserversToData() {


        model.item.observe(this, Observer { item ->

            Glide.with(this)
                .load(item?.image)
                .into(detailImage)
            detailBrand.text = item?.brand
            detailName.text = item?.name
            detailSize.text = item?.size

            if (item?.comment != "") {
                commentText.text = item?.comment
                addCommentEditText.visibility = View.GONE
                addCommentButton.visibility = View.GONE
                commentText.visibility = View.VISIBLE
            }
            if (item?.isFavorite == true) favoriteCheckBox.isChecked = true

            model.currency.observe(this, Observer { currency ->
                detailPrice.text = String.format(
                    "%.2f", ((Currency.EUR.coeff / currency.coeff) * item.price)
                )
                detailCurrency.text = currency.sign
            })

        })

    }

    private fun openWebView() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle(model.item.value?.name)

        val wv = WebView(this)
        println("https://en.aboutyou.de${model.item.value?.id}")
        wv.loadUrl("https://en.aboutyou.de${model.item.value?.id}")
        wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        alert.setView(wv)
        alert.setNegativeButton("Close",
            DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        alert.show()
    }

    private fun addComment() {

        val text = addCommentEditText.text.toString()

        if (text.length > 3) {
            model.item.value?.comment = text
            model.updateItem()
            commentText.text = text
            addCommentEditText.visibility = View.GONE
            addCommentButton.visibility = View.GONE
            commentText.visibility = View.VISIBLE
        }

    }
}