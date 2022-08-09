package uz.gita.dictionary.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.dictionary.data.room.WordData
import uz.gita.dictionary.viewModel.FavViewModel
import uz.gita.dictionary.repository.impl.WordRepositoryImpl

class FavViewModelImpl : FavViewModel, ViewModel() {
    private val repos = WordRepositoryImpl()
    override val favs: LiveData<List<WordData>> = repos.getFavs()

    override fun update(wordData: WordData) {
        repos.update(wordData)
    }
}