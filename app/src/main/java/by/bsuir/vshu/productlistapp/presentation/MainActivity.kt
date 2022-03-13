package by.bsuir.vshu.productlistapp.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.util.StateSet
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.bsuir.vshu.productlistapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        title = findViewById(R.id.title)
        title.background = createRectSelector(
            Color.WHITE,
            floatArrayOf(20f, 20f, 20f, 20f), fillAfterRipple = true
        )
        title.isClickable = true

        navView.setupWithNavController(navController)
    }

    fun createRectSelector(
        color: Int,
        radii: FloatArray? = null,
        fillAfterRipple: Boolean = false
    ): Drawable {
        val colorStateList = ColorStateList(
            arrayOf(StateSet.WILD_CARD),
            intArrayOf(0xff000000.toInt())
        )

        val radiiArray = FloatArray(8)
        if (radii != null) {
            for (i in 0 until radii.size) {
                radiiArray[i * 2] = radii[i]
                radiiArray[i * 2 + 1] = radii[i]
            }
        }

        val defDrawable = ShapeDrawable(RoundRectShape(radiiArray, null, null))
        if (fillAfterRipple) {
            defDrawable.paint.color = color
        } else {
            defDrawable.paint.color = Color.TRANSPARENT
        }

        val rippleDrawable = ShapeDrawable(RoundRectShape(radiiArray, null, null))

        return RippleDrawable(colorStateList, defDrawable, rippleDrawable)
    }
}