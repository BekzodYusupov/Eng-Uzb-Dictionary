package uz.gita.dictionary.viewModel

import androidx.lifecycle.LiveData
import uz.gita.dictionary.data.room.WordData

interface FavViewModel {
    val favs: LiveData<List<WordData>>
    fun update(wordData: WordData)
}