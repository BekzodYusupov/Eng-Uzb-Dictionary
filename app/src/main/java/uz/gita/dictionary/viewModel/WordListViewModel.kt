package uz.gita.dictionary.viewModel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionary.data.room.WordData

interface WordListViewModel {

    val cursorLiveData: LiveData<Cursor>
    val showWordInfoLiveData: LiveData<WordData>
    val updateItemLiveData: LiveData<Int>

    fun showInfo(wordData: WordData)

    fun changeFavourite(wordData: WordData)

    fun filter(query: String)


}