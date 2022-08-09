package uz.gita.dictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.data.room.WordData

class FavAdapter : RecyclerView.Adapter<FavAdapter.VH>() {
    private val oldList: ArrayList<WordData> = ArrayList()
    private var itemClickListener: ((WordData) -> Unit)? = null
    private var favouriteClickListener: ((WordData) -> Unit)? = null
    private var audioClickListener: ((word:String) -> Unit)? = null

    fun submitList(newList: List<WordData>) {
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setItemClickListener(block: (WordData) -> Unit) {
        itemClickListener = block
    }

    fun setFavouriteClickListener(block: (WordData) -> Unit) {
        favouriteClickListener = block
    }

    fun onAudioClickListener(block: (word:String)->Unit) {
        audioClickListener = block
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val txtWord: TextView = view.findViewById(R.id.txtWord)
        private val ivStar: ImageView = view.findViewById(R.id.ivStar)

        private val ivAudio: ImageView = view.findViewById(R.id.ivAudio)
        private val tvTranscript: TextView = view.findViewById(R.id.tvTranscript)
        private val tvType: TextView = view.findViewById(R.id.tvType)

        fun bind() {
            val item = oldList[adapterPosition]
            txtWord.text = item.english
            tvType.text = item.type
            tvTranscript.text = item.transcript

            if (item.isFavourite == 0) {
                ivStar.setImageResource(R.drawable.start_outlined)
            } else {
                ivStar.setImageResource(R.drawable.star_filled)
            }
        }

        init {
            view.setOnClickListener {
                itemClickListener?.invoke(oldList[adapterPosition])
            }

            ivStar.setOnClickListener {
                favouriteClickListener?.invoke(oldList[adapterPosition])
            }

            ivAudio.setOnClickListener {
                audioClickListener?.invoke(oldList[adapterPosition].english)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = oldList.size
}