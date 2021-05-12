package dk.sdu.stocktracker.impl.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dk.sdu.stocktracker.api.IStockDAO
import dk.sdu.stocktracker.api.IStock

@Database(entities = arrayOf(IStock::class), version = 1)
abstract class LocalStockStorage : RoomDatabase() {
    abstract fun stockDao(): IStockDAO

    companion object {
        private var INSTANCE: LocalStockStorage? = null
        fun getLocalStockStorage(context: Context): LocalStockStorage? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, LocalStockStorage::class.java, "stock-storage").allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}