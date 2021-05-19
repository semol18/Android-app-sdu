package dk.sdu.stocktracker.impl.storage

import androidx.room.*

@Dao
interface IStockEntityDAO {
    @Insert
    fun saveStock(stock: StockEntity)

    @Delete
    fun removeStock(stock: StockEntity)

    @Query("SELECT * FROM stock WHERE symbol = :symbol")
    fun getStock(symbol: String) : StockEntity

    @Query("SELECT * FROM stock")
    fun getStocks() : Array<StockEntity>
}