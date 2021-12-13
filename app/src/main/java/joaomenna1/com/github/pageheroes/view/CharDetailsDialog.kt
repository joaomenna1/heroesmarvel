package joaomenna1.com.github.pageheroes.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import joaomenna1.com.github.pageheroes.data.Character
import joaomenna1.com.github.pageheroes.databinding.DialogCharDetailsBinding

class CharDetailsDialog(
    val character: Character,
): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogCharDetailsBinding.inflate(inflater).let {
        Log.e("hero", character.toString())
        it.apply {
            lifecycleOwner = viewLifecycleOwner
            item = character
        }

        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        const val TAG = "CHAR_DETAILS_DIALOG"
    }
}