package by.bsuir.vshu.productlistapp.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.util.Category
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val model by viewModels<HomeViewModel>()

    private var tabLayout: TabLayout? = null
    private var tabMen: TabLayout.Tab? = null
    private var tabWomen: TabLayout.Tab? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = getView()?.findViewById(R.id.tabLayout)
        tabMen = tabLayout!!.newTab().apply { text = "Men's" }
        tabWomen = tabLayout!!.newTab().apply { text = "Women's" }
        tabLayout?.addTab(tabMen!!)
        tabLayout?.addTab(tabWomen!!)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.contentDescription!!.contains("Men's")) {
                    model.itemListState.value?.category = Category.MEN
                } else {
                    model.itemListState.value?.category = Category.WOMEN
                }
                model.itemListState.forceRefresh()
                println("Current category is: ${model.itemListState.value?.category}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        recyclerView = getView()?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)


        model.itemListState.observe(viewLifecycleOwner, Observer {
            println("Tab changed")
            recyclerView?.adapter =
                ItemAdapter(it.items.filter { item -> item.category == it.category.s  || item.category == it.category.getComplementCategory() })
            val gothicFont: Typeface = resources.getFont(R.font.gothic)
            tabMen!!.text = "Men's (${model.getItemCountByCategory(Category.MEN)})"
            tabWomen!!.text = "Women's (${model.getItemCountByCategory(Category.WOMEN)})"
            tabLayout!!.setFont(gothicFont)
        })

    }


}

fun TabLayout.setFont(font: Typeface) {
    val vg = this.getChildAt(0) as ViewGroup
    for (i: Int in 0..vg.childCount) {
        val vgTab = vg.getChildAt(i) as ViewGroup?
        vgTab?.let {
            for (j: Int in 0..vgTab.childCount) {
                val tab = vgTab.getChildAt(j)
                if (tab is TextView) {
                    tab.typeface = font
                }
            }
        }
    }
}




