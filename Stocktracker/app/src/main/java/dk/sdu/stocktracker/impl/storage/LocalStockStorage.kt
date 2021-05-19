package dk.sdu.stocktracker.impl.storage

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dk.sdu.stocktracker.api.IStock
import dk.sdu.stocktracker.api.storage.IStockStorage
import java.lang.RuntimeException

@Database(entities = [StockEntity::class], version = 1)
abstract class LocalStockStorage : IStockStorage, RoomDatabase() {
    internal abstract fun stockDao(): IStockEntityDAO

    companion object {
        private lateinit var INSTANCE: LocalStockStorage;
        fun getInstance(context: Context): LocalStockStorage {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, LocalStockStorage::class.java, "stock-storage").allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

    override fun saveStock(stock: IStock) {
        // Try catch for duplicate.
        try {
            stockDao().saveStock(stockToStockEntity(stock))
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    override fun deleteStock(stock: IStock) {
        stockDao().removeStock(stockToStockEntity(stock))
    }

    override fun getStockBySymbol(symbol: String) : IStock {
       //return stockDao().getStock(symbol)
       throw RuntimeException()
    }

    override fun getAllStock() : Array<IStock> {
        //stockDao().getStocks();
        //TODO: Implement a way to get a list of all stocks. This may have serious performance issues since we would currently require one api call for each stock.
        return emptyArray();
    }

    private fun stockToStockEntity(stock: IStock) : StockEntity {
        var stockEntity = StockEntity(stock.getSymbol());
        return stockEntity;
    }

    private fun stockEntityToIStock(stock: StockEntity) {
        //TODO: Implement api call
    }
}