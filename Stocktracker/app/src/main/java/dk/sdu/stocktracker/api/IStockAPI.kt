package dk.sdu.stocktracker.api

import dk.sdu.stocktracker.impl.Stock
import kotlinx.coroutines.flow.Flow

interface IStockAPI {
    fun searchStock(searchString: String) : Array<Stock>;
    fun getPrice(symbol: String) : Flow<IPrice?>;
}