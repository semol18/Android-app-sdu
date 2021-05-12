package dk.sdu.stocktracker.api

interface ILocalStockStorage {
    fun saveStock(stock: IStock);
    fun removeStock(stock: IStock);
    fun getStocks() : Array<IStock>; // Might want to only return the symbols of the stocks to allow greater control over when to fetch from API
}