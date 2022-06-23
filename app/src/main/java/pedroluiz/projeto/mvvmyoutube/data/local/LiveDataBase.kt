package pedroluiz.projeto.mvvmyoutube.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pedroluiz.projeto.mvvmyoutube.model.Live

@Database(entities = [Live::class ], version = 1)
abstract class LiveDataBase : RoomDatabase() {

    abstract fun liveDao(): LiveDao

    companion object {

        private val DATABASE_NAME = "liveDB"

        @Volatile
        private var INSTANCE: LiveDataBase? = null

        fun getInstance(context: Context): LiveDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LiveDataBase::class.java, DATABASE_NAME
            ).build()

    }
}

