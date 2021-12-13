package joaomenna1.com.github.pageheroes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import joaomenna1.com.github.pageheroes.data.Character
import joaomenna1.com.github.pageheroes.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val recyclerView: RecyclerView,
    private val items: List<Character>,
    private val listener: (char: Character, pos: Int) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position, listener)
        holder.setItemSize()
    }

    override fun getItemId(position: Int): Long = items[position].id

    override fun getItemCount(): Int = items.size

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Character, pos: Int, listener: (char: Character, pos: Int) -> Unit) {
                binding.item = item

                binding.root.setOnClickListener {
                    listener.invoke(item, pos)
                }
            }

        fun setItemSize() {
            val height = recyclerView.height

            val params = ConstraintLayout.LayoutParams(MATCH_PARENT, height/4)
            binding.root.layoutParams = params
        }

    }
}