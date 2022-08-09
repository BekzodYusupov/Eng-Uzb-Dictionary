package uz.gita.dictionary.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dictionary")
data class WordData(
    @PrimaryKey
    val id: Long,
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val countable: String,
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Int
) : Serializable