package dk.sdu.stocktracker.api

import dk.sdu.stocktracker.impl.StockAPI

interface IStockAPI {
    fun searchStock(searchString: String) : ISearchStockResult;
    fun updateStock(stock: IStock): IStock;
}

interface ISearchStockResult {
    fun getResult() : Boolean;
    fun getStock() : IStock;
}