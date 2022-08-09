package uz.gita.dictionary.ui.screens

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.gita.dictionary.R
import uz.gita.dictionary.data.room.WordData
import uz.gita.dictionary.viewModel.WordViewModel
import uz.gita.dictionary.viewModel.impl.WordViewModelImpl
import java.util.*

class WordActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var engWord: TextView
    private lateinit var uzWord: TextView
    private lateinit var type: TextView
    private lateinit var transcript: TextView
    private lateinit var count: TextView
    private lateinit var briAudio: ImageView
    private lateinit var usAudio: ImageView
    private lateinit var favState: ImageView
    private lateinit var btnback: ImageView
    private lateinit var wordViewModel: WordViewModel
    private lateinit var wordData: WordData
    private var id: Int = -1
    private lateinit var ttsUS: TextToSpeech
    private lateinit var ttsBritish: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        id = intent.getLongExtra("ID", -1).toInt()
        init()
        load()
        btnback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        favState.setOnClickListener {
            if (wordData.isFavourite == 1) {
                wordData.isFavourite = 1
                Toast.makeText(this, "${wordData.isFavourite}", Toast.LENGTH_SHORT).show()
                favState.setImageResource(R.drawable.start_outlined)
                wordViewModel.update(wordData)
            } else {
                wordData.isFavourite = 0
                Toast.makeText(this, "${wordData.isFavourite}", Toast.LENGTH_SHORT).show()
                favState.setImageResource(R.drawable.star_filled)
                wordViewModel.update(wordData)
            }
        }

        usAudio.setOnClickListener {
            ttsUS!!.speak(engWord.text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        briAudio.setOnClickListener {
            ttsBritish!!.speak(engWord.text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun init() {
        ttsUS = TextToSpeech(this, this)
        ttsBritish = TextToSpeech(this, this)

        engWord = findViewById(R.id.wordInWordScreen)
        uzWord = findViewById(R.id.translationInWordScreen)
        type = findViewById(R.id.tvTypeWordScreen)
        transcript = findViewById(R.id.tvTranscriptWordScreen)
        count = findViewById(R.id.tvCountWordScreen)
        briAudio = findViewById(R.id.ivAudioWordScreenBritish)
        usAudio = findViewById(R.id.ivAudioWordScreenUS)
        favState = findViewById(R.id.ivfavStateWordScreen)
        btnback = findViewById(R.id.btnBack)
        wordViewModel = ViewModelProvider(this)[WordViewModelImpl::class.java]
        wordData = wordViewModel.getWord(id)
    }

    private fun load() {
        if (id == -1) return
        engWord.text = wordData.english
        uzWord.text = wordData.uzbek
        type.text = wordData.type
        transcript.text = wordData.transcript
        count.text = wordData.countable

        if (wordData.isFavourite == 1) {
            favState.setImageResource(R.drawable.star_filled)
        } else {
            favState.setImageResource(R.drawable.start_outlined)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsUS.language = Locale.US
            ttsBritish.language = Locale.UK
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS
        ttsUS.stop()
        ttsBritish.stop()
        ttsUS.shutdown()
        ttsBritish.shutdown()
        super.onDestroy()
    }
}