package dk.sdu.stocktracker.api.storage

import dk.sdu.stocktracker.impl.Stock
import kotlinx.coroutines.flow.Flow

interface IStockStorage {
    fun saveStock(stock: Stock);
    fun deleteStock(stock: Stock);
    fun getStockBySymbol(symbol: String): Stock;
    fun getAllStock() : Flow<Array<Stock>>;
}