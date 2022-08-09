package uz.gita.dictionary.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionary.data.room.WordData

interface WordRepository {

    fun getWord(id:Int):WordData

    fun getWordsCursor(): Cursor

    fun getFilteredWordsCursor(query:String): Cursor

    fun update(wordData: WordData)

    fun getFavs():LiveData<List<WordData>>

}