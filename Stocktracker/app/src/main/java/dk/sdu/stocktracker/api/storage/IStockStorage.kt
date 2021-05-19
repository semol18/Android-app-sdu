package dk.sdu.stocktracker.api.storage

import dk.sdu.stocktracker.api.IStock

interface IStockStorage {
    fun saveStock(stock: IStock);
    fun deleteStock(stock: IStock);
    fun getAllStock() : Array<IStock>;
}