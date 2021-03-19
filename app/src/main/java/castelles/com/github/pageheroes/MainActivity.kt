package castelles.com.github.pageheroes

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hello there, how are you?
 *
 * You probably know my name, and all the other information in my curriculum.
 * I just wanted you to know why I decided to use some of the technologies
 * and/or patterns while implementing this app.
 * Well, writing this in english was one of them.
 *
 * But let's move forward.
 * This app is obviously a small application in concerning to everything an app developer
 * working in the industry faces on a daily basis.
 * Because of that, some of my choices are a little exaggerated, but I thought it'd be
 * important for you to know I'm aware of some technologies (let's put it that way) in
 * android development. The 'killing an ant with a bazooka' technologies in this case are
 * navigation, coroutines (I ended up using this one just one time), the MVVM architecture
 * (here, a MVC would do a good job as well) and so on.
 *
 * Hope you enjoy it.
 *
 * The link to this app git repositorie is
 * https://github.com/castelles/PageHeroes
 *
 * @author Caio Arthur Sales Telels
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