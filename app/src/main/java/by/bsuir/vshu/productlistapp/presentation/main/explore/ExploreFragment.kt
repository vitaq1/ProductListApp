package by.bsuir.vshu.productlistapp.presentation.main.explore

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.detail.DetailActivity
import by.bsuir.vshu.productlistapp.presentation.main.SharedViewModel
import by.bsuir.vshu.productlistapp.presentation.main.home.ItemAdapter
import by.bsuir.vshu.productlistapp.presentation.main.home.OnItemClickListener
import by.bsuir.vshu.productlistapp.presentation.main.home.setFont
import by.bsuir.vshu.productlistapp.util.Category
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout

class ExploreFragment : Fragment() {

    private val model by activityViewModels<SharedViewModel>()

    private lateinit var optionsLinearLayout: LinearLayout
    private lateinit var filterList: CheckBox
    private lateinit var priceSlider: Slider
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    private fun initViews() {
        optionsLinearLayout = requireView().findViewById(R.id.moreFilterOptions)

        filterList = requireView().findViewById(R.id.filterList)
        filterList.apply {
            setOnClickListener {
                if (filterList.isChecked) optionsLinearLayout.visibility = View.VISIBLE
                else optionsLinearLayout.visibility = View.GONE

            }
        }

        priceSlider = requireView().findViewById(R.id.priceSlider)
        priceSlider.apply {
            valueTo =
                model.itemListState.value?.items?.maxWithOrNull(Comparator.comparingDouble { it.price })?.price!!.toFloat()
        }

        recyclerView = requireView().findViewById(R.id.recyclerExploreView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setObservers(){

        model.itemListState.observe(viewLifecycleOwner, Observer {

            var listener: OnItemClickListener

            recyclerView.adapter =
                ItemAdapter(it.items.filter { item -> item.category == it.category.s },
                    OnItemClickListener { view, id ->
                        openDetailActivity(view, id)
                    }, it.currency

                )
        })

    }

    private fun openDetailActivity(view: View, itemId: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("itemId", itemId)
        intent.putExtra("currency", model.itemListState.value?.currency?.name)
        val pImage: Pair<View, String> =
            Pair.create(view.findViewById(R.id.itemListImageView) as View?, "itemTransitionImage")
        val pBrand: Pair<View, String> =
            Pair.create(view.findViewById(R.id.itemListBrandText), "itemTransitionBrand")
        val pName: Pair<View, String> =
            Pair.create(view.findViewById(R.id.itemListNameText) as View?, "itemTransitionName")
        val pPrice: Pair<View, String> =
            Pair.create(view.findViewById(R.id.itemListPriceText) as View?, "itemTransitionPrice")
        val pCurrency: Pair<View, String> =
            Pair.create(
                view.findViewById(R.id.itemListCurrencyText) as View?,
                "itemTransitionCurrency"
            )
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            pImage,
            pBrand,
            pName,
            pPrice,
            pCurrency
        )
        startActivity(intent, options.toBundle())
    }

    override fun onPause() {
        filterList.isChecked = false
        super.onPause()
    }

}

