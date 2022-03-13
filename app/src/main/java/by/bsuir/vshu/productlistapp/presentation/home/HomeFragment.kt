package by.bsuir.vshu.productlistapp.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.SharedViewModel
import by.bsuir.vshu.productlistapp.presentation.forceRefresh
import by.bsuir.vshu.productlistapp.util.Category
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val model by activityViewModels<SharedViewModel>()

    private var tabLayout: TabLayout? = null
    private var tabShoes: TabLayout.Tab? = null
    private var tabAccessories: TabLayout.Tab? = null
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
        tabShoes = tabLayout!!.newTab().apply { text = "Shoes" }
        tabAccessories = tabLayout!!.newTab().apply { text = "Accessories" }
        tabLayout?.addTab(tabShoes!!)
        tabLayout?.addTab(tabAccessories!!)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.contentDescription!!.contains("Shoes")) {
                    model.itemListState.value?.category = Category.SHOES
                } else {
                    model.itemListState.value?.category = Category.ACCESSORIES
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
                ItemAdapter(it.items.filter { item -> item.category == it.category.s })
            val gothicFont: Typeface = resources.getFont(R.font.gothic)
            tabShoes!!.text = "Shoes (${model.getItemCountByCategory(Category.SHOES)})"
            tabAccessories!!.text =
                "Accessories (${model.getItemCountByCategory(Category.ACCESSORIES)})"
            tabLayout!!.setFont(gothicFont)
        })

        checkInternetConnection()


    }

    private fun checkInternetConnection() {
        if (!model.isInternetConnected(requireContext())) {
            val toast =
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            val toastContainer = toast.view as LinearLayout
            val catImage = ImageView(requireContext())
            catImage.setImageResource(R.drawable.ic_error_connection)
            toastContainer.addView(catImage, 0)
            toast.show()
        }
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




