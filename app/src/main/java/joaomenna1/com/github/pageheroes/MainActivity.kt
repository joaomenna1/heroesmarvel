package joaomenna1.com.github.pageheroes

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *
 * @author João Menna
 *
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        fun hideKeyboard(activity: MainActivity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            view?.let {  } ?: run {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

}