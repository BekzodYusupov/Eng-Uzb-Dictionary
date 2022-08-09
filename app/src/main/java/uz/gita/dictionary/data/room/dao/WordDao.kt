package uz.gita.dictionary.data.room.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.dictionary.data.room.WordData

@Dao
interface WordDao {

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWord(id: Int): WordData

    @Insert
    fun insert(wordData: WordData)

    @Delete
    fun delete(wordData: WordData)

    @Update
    fun update(wordData: WordData)

    @Query("SELECT * FROM dictionary")
    fun getEngUzCursor(): Cursor

    @Query("SELECT * FROM dictionary WHERE is_favourite=1")
    fun getFavourite(): Cursor

    @Query("SELECT * FROM dictionary WHERE is_favourite=1")
    fun getFavs(): LiveData<List<WordData>>

    @Query("SELECT * FROM dictionary ORDER BY uzbek")
    fun getUzEngCursor(): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE '%' || :query || '%'")
    fun getFilteredCursor(query: String): Cursor


}