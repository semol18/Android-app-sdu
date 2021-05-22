package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IStockAPI
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class StockAPITest {

    @Test
    fun searchStockThatDoesNotExist() {
        var api: IStockAPI = StockAPI.getInstance();
        val result = api.searchStock("APPLE INC");
        assertFalse(result!!.name.equals("APPLE INC"))
    }

    @Test
    fun searchStockThatExists() {
        var api: IStockAPI = StockAPI.getInstance();
        val result = api.searchStock("AAPL");
        assertTrue(result!!.symbol == "AAPL")
    }

    @Test
    fun updateStock() {
        /*var api: IStockAPI = StockAPI();
        val stock = api.updateStock("GME");
        assert(stock.getSymbol() == "GME");
        assert(stock.getPrice().getPrice() != 0.0);
        assert(stock.getPrice().getTimeStamp().before(Date()));*/
    }

    @Test
    fun getPrice() {
        var api: IStockAPI = StockAPI.getInstance();
        val price = api.getPrice("GME");
        assert(price.getTimeStamp().before(Date()));
        assert(price.getPrice() != 0.0)
    }
}