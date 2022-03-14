package by.bsuir.vshu.productlistapp.presentation.main.profile


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.bsuir.vshu.productlistapp.R

class InfoDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Лабораторная работа № 2")
                .setMessage("Шустовский Виталий\n951008")
                .setIcon(R.drawable.ic_info)
                .setPositiveButton("Close") {
                        dialog, id ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
