package dk.sdu.stocktracker.impl.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dk.sdu.stocktracker.impl.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDAO {
    @Insert
    fun saveStock(stock: Stock)

    @Delete
    fun removeStock(stock: Stock)

    @Query("SELECT * FROM stock WHERE symbol = :symbol")
    fun getStock(symbol: String) : Stock

    @Query("SELECT * FROM stock")
    fun getLiveStocks(): Flow<Array<Stock>>

    @Query("SELECT * FROM stock")
    fun getStocks() : Array<Stock>
}