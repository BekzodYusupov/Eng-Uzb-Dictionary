package uz.gita.dictionary.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.dictionary.data.room.WordData
import uz.gita.dictionary.repository.WordRepository
import uz.gita.dictionary.repository.impl.WordRepositoryImpl
import uz.gita.dictionary.viewModel.WordViewModel

class WordViewModelImpl:WordViewModel, ViewModel() {
    private val repository: WordRepository = WordRepositoryImpl()
    override val word: MutableLiveData<WordData> = MutableLiveData()

    override fun update(wordData: WordData) {
        repository.update(wordData)
    }

    override fun getWord(id: Int): WordData {
        return repository.getWord(id)
    }
}