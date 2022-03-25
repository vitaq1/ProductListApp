package by.bsuir.vshu.productlistapp.presentation.main.explore

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
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
import by.bsuir.vshu.productlistapp.util.Currency
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout

class ExploreFragment : Fragment() {

    private val model by activityViewModels<SharedViewModel>()

    private lateinit var optionsLinearLayout: LinearLayout
    private lateinit var searchView: SearchView
    private lateinit var filterList: CheckBox
    private lateinit var nameCheckBox: CheckBox
    private lateinit var brandCheckBox: CheckBox
    private lateinit var favoriteCheckBox: CheckBox
    private lateinit var priceSlider: Slider
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: ImageView

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

        searchView = requireView().findViewById(R.id.searchView)

        filterList = requireView().findViewById(R.id.filterList)
        filterList.apply {
            setOnClickListener {
                if (filterList.isChecked) optionsLinearLayout.visibility = View.VISIBLE
                else optionsLinearLayout.visibility = View.GONE

            }
        }

        nameCheckBox = requireView().findViewById(R.id.nameCheckBox)

        brandCheckBox = requireView().findViewById(R.id.brandCheckBox)

        favoriteCheckBox = requireView().findViewById(R.id.favoriteCheckBox)


        priceSlider = requireView().findViewById(R.id.priceSlider)
        priceSlider.apply {
            valueTo =
                    model.itemListState.value?.items?.maxWithOrNull(Comparator.comparingDouble { it.price })?.price!!.toFloat()
            value = valueTo
        }


        recyclerView = requireView().findViewById(R.id.recyclerExploreView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        searchButton = requireView().findViewById(R.id.searchButton)
        searchButton.apply {
            setOnClickListener {
                var nameFlag = true
                var brandFlag = true
                var favFlag = true
                recyclerView.adapter =
                    ItemAdapter(
                        model.itemListState.value!!.items.filter { item ->
                            nameFlag =
                                if (nameCheckBox.isChecked) item.name.contains(
                                    searchView.query,
                                    true
                                ) else true
                            brandFlag =
                                if (brandCheckBox.isChecked) item.brand.contains(
                                    searchView.query,
                                    true
                                ) else true
                            if (nameCheckBox.isChecked && brandCheckBox.isChecked) {
                                nameFlag = item.name.contains(
                                    searchView.query,
                                    true
                                ) || item.brand.contains(searchView.query, true)
                                brandFlag = nameFlag
                            }
                            favFlag = if (favoriteCheckBox.isChecked) {
                                item.isFavorite
                            } else true

                            nameFlag && brandFlag && favFlag && item.price <= priceSlider.value

                        },
                        OnItemClickListener { view, id ->
                            openDetailActivity(view, id)
                        }, model.itemListState.value!!.currency

                    )
            }
        }
    }

    private fun setObservers() {

        model.itemListState.observe(viewLifecycleOwner, Observer {

            recyclerView.adapter =
                ItemAdapter(
                    it.items,
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


    override fun onResume() {
        model.updateItems()
        filterList.isChecked = false
        brandCheckBox.isChecked = false
        favoriteCheckBox.isChecked = false
        searchView.setQuery("", false)
        optionsLinearLayout.visibility = View.GONE

        super.onResume()
    }

}

