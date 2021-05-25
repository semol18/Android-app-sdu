package dk.sdu.stocktracker.impl

import androidx.room.Entity
import androidx.room.PrimaryKey
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStockAPI
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey val symbol: String,
    val name: String,
) {

    fun getPrice(stockAPI: IStockAPI): Flow<IPrice?> {
        return stockAPI.getPrice(this.symbol);
    }

}