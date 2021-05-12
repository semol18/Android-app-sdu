package dk.sdu.stocktracker.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IStockDAO {
    @Insert
    fun saveStock(stock: IStock?)

    @Delete
    fun removeStock(stock: IStock?)

    @Query("SELECT * FROM stock")
    fun getStocks() : Array<IStock> // Might want to only return the symbols of the stocks to allow greater control over when to fetch from API
}