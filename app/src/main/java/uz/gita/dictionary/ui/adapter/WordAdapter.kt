package uz.gita.dictionary.ui.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.gita.dictionary.R
import uz.gita.dictionary.data.room.WordData

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordItemViewHolder>() {

    private var cursor: Cursor? = null
    private var itemClickListener: ((WordData) -> Unit)? = null
    private var favouriteClickListener: ((WordData) -> Unit)? = null
    private var audioClickListener: ((word:String) -> Unit)? = null


    fun setItemClickListener(block: (WordData) -> Unit) {
        itemClickListener = block
    }

    fun setFavouriteClickListener(block: (WordData) -> Unit) {
        favouriteClickListener = block
    }

    fun setAudioClickListener(block: (word:String)->Unit) {
        audioClickListener = block
    }

    fun submitCursor(cursor: Cursor) {
        Timber.d("Submitted Cursor")
        this.cursor = cursor
        Timber.d("Count ${cursor.count}")
        notifyDataSetChanged()
    }

    inner class WordItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtWord: TextView = view.findViewById(R.id.txtWord)
        private val ivStar: ImageView = view.findViewById(R.id.ivStar)

        private val ivAudio: ImageView = view.findViewById(R.id.ivAudio)
        private val tvTranscript: TextView = view.findViewById(R.id.tvTranscript)
        private val tvType: TextView = view.findViewById(R.id.tvType)

        fun bind() {
            cursor!!.moveToPosition(adapterPosition)
            val data = cursor!!.getWordData()

            txtWord.text = data.english
            tvType.text = data.type
            tvTranscript.text = data.transcript

            if (data.isFavourite == 0) {
                ivStar.setImageResource(R.drawable.start_outlined)
            } else {
                ivStar.setImageResource(R.drawable.star_filled)
            }
        }

        init {
            view.setOnClickListener {
                Timber.d("ITEM CLICKED $adapterPosition")
                cursor!!.moveToPosition(adapterPosition)
                val data = cursor!!.getWordData()
                itemClickListener?.invoke(data)
            }
            ivStar.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                val data = cursor!!.getWordData()
                favouriteClickListener?.invoke(data)
                if (data.isFavourite == 0) {
                    ivStar.setImageResource(R.drawable.start_outlined)
                } else {
                    ivStar.setImageResource(R.drawable.star_filled)
                }
            }

            ivAudio.setOnClickListener {
                cursor!!.moveToPosition(adapterPosition)
                val data = cursor!!.getWordData()
                audioClickListener?.invoke(data.english)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
    )

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int {
        val count = cursor?.count ?: 0
        Timber.d(count.toString())
        return count
    }
}

fun Cursor.getWordData(): WordData {
    return WordData(
        getLong(0),
        getString(1),
        getString(2),
        getString(3),
        getString(4),
        getString(5),
        getInt(6)
    )
}