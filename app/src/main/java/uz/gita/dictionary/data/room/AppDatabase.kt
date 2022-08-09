package uz.gita.dictionary.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.dictionary.data.room.dao.WordDao

@Database(
    entities = [WordData::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun init(context: Context) {
            INSTANCE = Room
                .databaseBuilder(context, AppDatabase::class.java, "app_db")
                .createFromAsset("dictionary.db")
                .allowMainThreadQueries()
                .build()
        }
        val instance: AppDatabase
            get() = INSTANCE!!
    }
}