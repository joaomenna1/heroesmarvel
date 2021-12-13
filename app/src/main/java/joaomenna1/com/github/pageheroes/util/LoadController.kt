package joaomenna1.com.github.pageheroes.util

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import joaomenna1.com.github.pageheroes.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * This one is a class I already had from a previous project
 * It's quite verbose because I made it a long time a go, but it's
 * functional.
 */
class LoadController(
    private val context: Context
) {

    companion object {
        const val VERTICAL_DIMEN = 100
        const val HORIZONTAL_DIMEN = 250
        const val TEXT_SIZE = 25
        const val COLOR = Color.CYAN
        const val TEXT_COLOR = Color.BLACK
    }

    private lateinit var dialog: AlertDialog
    private lateinit var params: LinearLayout.LayoutParams

    private var ySize = VERTICAL_DIMEN
    private var xSize = HORIZONTAL_DIMEN
    private var textSize = TEXT_SIZE
    private var color = COLOR
    private var textColor = TEXT_COLOR
    private var cancelable = false
    private var WRAP_CONTENT = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    private var FORMAT_LAYOUT_VERTICAL = true

    fun showStandardLoading(message: String?) {
        CoroutineScope(Main).launch {
            this@LoadController
                .setCancelable(false)
                .setColor(context.resources.getColor(R.color.marvel_red))
                .setTextSize(16)
                .setSize(150, 150)
                .setTextColor(Color.WHITE)
                .startWithMessage(message)
        }
    }

    init {
        val builder = AlertDialog.Builder(context)
        dialog = builder.create()
    }

    fun setColor(color: Int): LoadController {
        this.color = color
        return this
    }

    fun setCancelable(cancelable: Boolean): LoadController {
        this.cancelable = cancelable
        return this
    }

    fun setTextColor(textColor: Int): LoadController {
        this.textColor = textColor
        return this
    }

    fun setTextSize(size: Int): LoadController {
        textSize = size
        return this
    }

    fun setSize(x: Int, y: Int): LoadController {
        xSize = x
        ySize = y
        return this
    }

    fun setHorizontalLayout(): LoadController {
        FORMAT_LAYOUT_VERTICAL = false
        setSize(80, 80)
        return this
    }

    fun justLoad() {
        dismissIt()
        val layoutBackground = RelativeLayout(context)
        layoutBackground.gravity = Gravity.CENTER
        layoutBackground.layoutParams = WRAP_CONTENT
        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, 20, 0)
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        params = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.layoutParams = params
        layoutBackground.addView(progressBar)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(cancelable)
        builder.setView(layoutBackground)
        dialog = builder.create()
        dialog.show()
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutParams
    }

    fun startWithMessage(textToShow: String?) {
        dismissIt()
        val ll = LinearLayout(context)
        val progressBar = ProgressBar(context)
        if (FORMAT_LAYOUT_VERTICAL) {
            ll.orientation = LinearLayout.VERTICAL
            progressBar.setPadding(0, 0, 0, 50)
        } else {
            ll.orientation = LinearLayout.HORIZONTAL
            progressBar.setPadding(0, 0, 30, 0)
        }
        ll.setPadding(10, 30, 0, 30)
        ll.gravity = Gravity.CENTER
        var llParam = WRAP_CONTENT
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.isIndeterminate = true
        progressBar.layoutParams = llParam
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        llParam = WRAP_CONTENT
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = textToShow
        tvText.setTextColor(textColor)
        tvText.textSize = textSize.toFloat()
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialogTheme)

        builder.apply {
            setCancelable(cancelable)
            setView(ll)
            dialog = this.create()
        }

        dialog.show()

        val window = dialog.window
        if (FORMAT_LAYOUT_VERTICAL) window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun loadInAView(view: ScrollView?) {
        dismissIt()
        val layoutBackground = RelativeLayout(context)
        layoutBackground.gravity = Gravity.CENTER
        layoutBackground.layoutParams = WRAP_CONTENT
        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, 20, 0)
        progressBar.indeterminateDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        params = LinearLayout.LayoutParams(xSize, ySize)
        progressBar.layoutParams = params
        layoutBackground.addView(progressBar)
        dialog.show()
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun dismissIt() {
        if (dialog.isShowing) dialog.dismiss()
    }

    fun isShowing(): Boolean {
        return dialog.isShowing
    }

}