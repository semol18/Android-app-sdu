package dk.sdu.stocktracker.impl.storage

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dk.sdu.stocktracker.api.IStockAPI
import dk.sdu.stocktracker.api.storage.IStockStorage
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.impl.StockAPI
import kotlinx.coroutines.flow.*

@Database(entities = [Stock::class], version = 1)
abstract class LocalStockStorage : IStockStorage, RoomDatabase() {
    internal abstract fun stockDao(): StockDAO

    companion object {
        private lateinit var INSTANCE: LocalStockStorage;
        fun getInstance(context: Context): LocalStockStorage {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LocalStockStorage::class.java,
                    "stock-storage"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

    override fun saveStock(stock: Stock) {
        // Try catch for duplicate.
        try {
            stockDao().saveStock(stock)
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    override fun deleteStock(stock: Stock) {
        stockDao().removeStock(stock)
    }

    override fun getStockBySymbol(symbol: String): Stock {
        return stockDao().getStock(symbol);
    }

    override fun getAllStock(): Flow<Array<Stock>> {
        return stockDao().getLiveStocks().distinctUntilChanged();
    }
}