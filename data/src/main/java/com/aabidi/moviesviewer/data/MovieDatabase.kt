package com.aabidi.moviesviewer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aabidi.moviesviewer.data.movies.local.dao.MovieDao
import com.aabidi.moviesviewer.data.movies.local.entities.LocalMovie
import com.aabidi.moviesviewer.data.util.converters.DateTypeConverter

@Database(
    entities = [LocalMovie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "MovieDatabase"

        private var sInstance: MovieDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieDatabase {
            if (sInstance == null) {
                synchronized(this) {
                    if (sInstance == null)
                        sInstance = buildDatabase(context, MovieDatabase::class.java, DATABASE_NAME).build()
                }
            }

            return sInstance as MovieDatabase
        }

        private fun <T : RoomDatabase> buildDatabase(
            context: Context,
            dbClass: Class<T>,
            databaseName: String,
            inMemory: Boolean = false

        ): Builder<T> {

            // In Memory
            return if (inMemory) Room.inMemoryDatabaseBuilder(context, dbClass)

            // Persistent
            else Room.databaseBuilder(context, dbClass, databaseName)
        }
    }

    // Movie
    abstract fun movieDao(): MovieDao

}
