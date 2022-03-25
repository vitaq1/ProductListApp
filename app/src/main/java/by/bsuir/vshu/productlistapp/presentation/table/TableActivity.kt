package by.bsuir.vshu.productlistapp.presentation.table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.bsuir.vshu.productlistapp.R
import by.bsuir.vshu.productlistapp.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableActivity : AppCompatActivity() {

    private val model by viewModels<TableViewModel>()

    private var table: TableLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        table = findViewById(R.id.tableLayout)


        model.results.observe(this, Observer {
            fillTable(it)
        })
    }


    private fun fillTable(results: List<Result>) {

        var k: Int = 0
        for (result in results) {

            val tableRow = TableRow(this).apply { }
            val date = TextView(this).apply {
                setText(result.date)
                gravity = Gravity.CENTER
                textSize = 10F
            }
            val desc = TextView(this).apply {
                setText(result.desc)
                textSize = 10F
                gravity = Gravity.CENTER
            }
            tableRow.addView(date, 0)
            tableRow.addView(desc, 1)
            tableRow.setBackgroundResource(R.color.orange)
            table?.addView(tableRow)
            k++
        }

    }
}