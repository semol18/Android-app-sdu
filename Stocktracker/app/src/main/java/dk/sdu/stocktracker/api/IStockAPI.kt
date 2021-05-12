package dk.sdu.stocktracker.api

interface IStockAPI {
    fun searchStock(searchString: String) : Boolean;
    fun updateStock(stock: IStock);
}