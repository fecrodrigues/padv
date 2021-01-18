package br.com.creativedevmind.padv

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Advogado::class], version = 1, exportSchema = false)
public abstract class AdvogadoRoomDatabase : RoomDatabase() {
    abstract fun advogadoDAO(): AdvogadoDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the
// same time.
        @Volatile
        private var INSTANCE: AdvogadoRoomDatabase? = null
        fun getDatabase(context: Context): AdvogadoRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AdvogadoRoomDatabase::class.java,
                    "advogado_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}