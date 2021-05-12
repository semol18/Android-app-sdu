package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.ILocalStockStorage
import dk.sdu.stocktracker.api.IStock

class LocalStockStorage : ILocalStockStorage {
    override fun saveStock(stock: IStock) {
        TODO("Not yet implemented")
    }

    override fun removeStock(stock: IStock) {
        TODO("Not yet implemented")
    }

    override fun getStocks(): Array<IStock> {
        TODO("Not yet implemented")
    }
}