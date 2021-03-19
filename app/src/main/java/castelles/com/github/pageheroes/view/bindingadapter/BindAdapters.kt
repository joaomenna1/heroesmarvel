package castelles.com.github.pageheroes.view.bindingadapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindAdapters {

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @BindingAdapter("image_download")
    @JvmStatic
    fun setImage(view: ImageView, path: String) {
        Glide.with(view.context)
            .load(path)
            .fitCenter()
            .into(view)
    }
}