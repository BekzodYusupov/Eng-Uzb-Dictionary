package uz.gita.dictionary.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.viewModel.WordListViewModel
import uz.gita.dictionary.viewModel.impl.WordListViewModelImpl
import uz.gita.dictionary.ui.adapter.WordAdapter
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var listWords: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var emptyPlaceholder: View

    private var tts: TextToSpeech? = null

    private lateinit var ivfav:ImageView

    private val adapter: WordAdapter by lazy { WordAdapter() }

    private val viewModel: WordListViewModel by viewModels<WordListViewModelImpl>()

    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this,this)
        ivfav = findViewById(R.id.ivFavs)
        ivfav.setOnClickListener {
            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)
            finish()
        }

        listWords = findViewById(R.id.listWords)
        searchView = findViewById(R.id.searchView)
        emptyPlaceholder = findViewById(R.id.emptyPlaceholder)

        listWords.layoutManager = LinearLayoutManager(this)
        adapter.setItemClickListener {
            val intent = Intent(this, WordActivity::class.java)
            intent.putExtra("ID", it.id)
            startActivity(intent)
            finish()
        }
        adapter.setAudioClickListener {
            tts!!.speak(it, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        adapter.setFavouriteClickListener { data ->
            viewModel.changeFavourite(data)
        }
        listWords.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handler.postDelayed({
                    viewModel.filter(newText)
                }, 300)
                return true
            }

        })

        viewModel.showWordInfoLiveData.observe(this) {
            val bundle = Bundle()
            bundle.putSerializable("data", it)
        }

        viewModel.cursorLiveData.observe(this) {
            if (it.count == 0) {
                emptyPlaceholder.visibility = View.VISIBLE
            } else {
                emptyPlaceholder.visibility = View.GONE
            }
            adapter.submitCursor(it)
        }
        viewModel.updateItemLiveData.observe(this) {
            adapter.notifyItemChanged(it)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts!!.language = Locale.US
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}