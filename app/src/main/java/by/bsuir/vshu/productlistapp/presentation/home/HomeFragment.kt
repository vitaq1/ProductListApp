package by.bsuir.vshu.productlistapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.common.Category
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val model by viewModels<HomeViewModel>()

    private var tabLayout: TabLayout? = null
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
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.id == 0) model.getItemListState().value?.category = Category.MEN
                else model.getItemListState().value?.category = Category.WOMEN
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

            tabLayout?.addTab(tabLayout!!.newTab().apply { text = "Men's" })
            tabLayout?.addTab(tabLayout!!.newTab().apply { text = "Women's" })

            recyclerView = getView()?.findViewById(R.id.recyclerView)
            recyclerView?.layoutManager = LinearLayoutManager(context)


            model.getItemListState().observe(viewLifecycleOwner, Observer {
                recyclerView?.adapter = ItemAdapter(it.items)
            })
        }



}