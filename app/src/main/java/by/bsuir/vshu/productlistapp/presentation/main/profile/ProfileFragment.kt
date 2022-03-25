package by.bsuir.vshu.productlistapp.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.detail.DetailActivity
import by.bsuir.vshu.productlistapp.presentation.main.SharedViewModel
import by.bsuir.vshu.productlistapp.presentation.map.MapsActivity
import by.bsuir.vshu.productlistapp.presentation.table.TableActivity
import by.bsuir.vshu.productlistapp.util.ConnectionChecker
import by.bsuir.vshu.productlistapp.util.Currency


class ProfileFragment : Fragment() {

    private val model by activityViewModels<SharedViewModel>()

    private lateinit var currencyPicker: Spinner
    private lateinit var infoButton: ImageView
    private lateinit var tableButton: ImageView
    private lateinit var mapButton: ImageView
    private lateinit var dayNightSwitch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currencies: Array<String> =
            arrayOf(
                Currency.EUR.name + " " + Currency.EUR.sign,
                Currency.USD.name + " " + Currency.USD.sign,
                Currency.RUB.name + " " + Currency.RUB.sign,
                Currency.GBP.name + " " + Currency.GBP.sign
            )
        currencyPicker = requireView().findViewById(R.id.currencyPicker)

        infoButton = requireView().findViewById<ImageView?>(R.id.infoButton)
            .apply { setOnClickListener { showInfoDialogFragment() } }

        tableButton = requireView().findViewById<ImageView?>(R.id.tableButton)
            .apply { setOnClickListener { openTableActivity() } }

        mapButton = requireView().findViewById<ImageView?>(R.id.mapButton)
            .apply { setOnClickListener { showMap() } }

        dayNightSwitch = requireView().findViewById<SwitchCompat?>(R.id.themeSwitcher).apply {

            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

        }


        val adapter: ArrayAdapter<*> =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (!ConnectionChecker.isInternetConnected(requireContext())) {
            currencyPicker.isEnabled = false
        }
        currencyPicker.adapter = adapter
        currencyPicker.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                model.itemListState.value?.currency =
                    Currency.values().filter { it.sign == currencies[position].split(" ")[1] }[0]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        })


    }

    private fun showInfoDialogFragment() {
        val infoFragment = InfoDialogFragment()
        val manager: FragmentManager = requireActivity().supportFragmentManager

        val transaction: FragmentTransaction = manager.beginTransaction()
        infoFragment.show(transaction, "dialog")
    }

    private fun openTableActivity(){
        val intent = Intent(context, TableActivity::class.java)
        startActivity(intent)
    }

    private fun showMap() {
        val intent = Intent(context, MapsActivity::class.java)
        startActivity(intent)
    }


}
