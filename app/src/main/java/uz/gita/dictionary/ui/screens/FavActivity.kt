package uz.gita.dictionary.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.data.room.WordData
import uz.gita.dictionary.viewModel.impl.FavViewModelImpl
import uz.gita.dictionary.ui.adapter.FavAdapter
import java.util.*

class FavActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var container:RecyclerView
    lateinit var adapter:FavAdapter
    lateinit var viewModel: FavViewModelImpl
    lateinit var ivback:ImageView
    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        tts = TextToSpeech(this,this)
        init()
        ivback.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        viewModel = ViewModelProvider(this)[FavViewModelImpl::class.java]

        viewModel.favs.observe(this){
            adapter.submitList(it)
        }

        adapter.setFavouriteClickListener {
            viewModel.update(WordData(it.id,it.english,it.type,it.transcript,it.uzbek,it.countable, 1))
            adapter.submitList(viewModel.favs.value!!)
        }
        adapter.setItemClickListener {
            val intent = Intent(this, WordActivity::class.java)
            intent.putExtra("ID",it.id)
            startActivity(intent)
        }
        adapter.onAudioClickListener {
            tts!!.speak(it,TextToSpeech.QUEUE_FLUSH,null,"")
        }
    }

    private fun init() {
        container = findViewById(R.id.containerFavs)
        adapter = FavAdapter()
        container.adapter = adapter
        ivback = findViewById(R.id.ivBacktoHome)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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