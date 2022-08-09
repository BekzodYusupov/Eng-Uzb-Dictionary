package uz.gita.dictionary.viewModel

import androidx.lifecycle.LiveData
import uz.gita.dictionary.data.room.WordData

interface WordViewModel {
    val word:LiveData<WordData>
    fun update(wordData: WordData)
    fun getWord(id:Int):WordData
}