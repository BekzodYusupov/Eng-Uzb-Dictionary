package uz.gita.dictionary.repository.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionary.data.room.AppDatabase
import uz.gita.dictionary.data.room.WordData
import uz.gita.dictionary.repository.WordRepository

class WordRepositoryImpl : WordRepository {

    private val wordDao = AppDatabase.instance.getWordDao()

    override fun getWord(id: Int): WordData {
        return wordDao.getWord(id)
    }

    override fun getWordsCursor(): Cursor = wordDao.getEngUzCursor()

    override fun getFilteredWordsCursor(query: String): Cursor = wordDao.getFilteredCursor(query)

    override fun update(wordData: WordData) {
        if (wordData.isFavourite == 0) {
            wordData.isFavourite = 1
        } else {
            wordData.isFavourite = 0
        }
        wordDao.update(wordData)
    }

    override fun getFavs(): LiveData<List<WordData>> {
        return wordDao.getFavs()
    }
}