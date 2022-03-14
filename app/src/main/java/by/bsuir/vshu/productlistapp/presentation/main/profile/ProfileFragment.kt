package by.bsuir.vshu.productlistapp.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.presentation.main.SharedViewModel
import by.bsuir.vshu.productlistapp.util.Currency


class ProfileFragment : Fragment() {

    private val model by activityViewModels<SharedViewModel>()

    private lateinit var currencyPicker: Spinner
    private lateinit var infoButton: ImageView

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
                Currency.USD.name + " " + Currency.USD.sign,
                Currency.EUR.name + " " + Currency.EUR.sign,
                Currency.RUB.name + " " + Currency.RUB.sign,
                Currency.GBP.name + " " + Currency.GBP.sign
            )
        currencyPicker = requireView().findViewById(R.id.currencyPicker)
        infoButton = requireView().findViewById<ImageView?>(R.id.infoButton).apply { setOnClickListener { showInfoDialogFragment() } }


        val adapter: ArrayAdapter<*> =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyPicker.adapter = adapter
        currencyPicker.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                model.currentCurrency.value =
                    Currency.values().filter { it.sign == currencies[position].split(" ")[1] }[0]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        })

    }

    fun showInfoDialogFragment() {
        val infoFragment = InfoDialogFragment()
        val manager: FragmentManager = requireActivity().supportFragmentManager

        val transaction: FragmentTransaction = manager.beginTransaction()
        infoFragment.show(transaction, "dialog")
    }


}
