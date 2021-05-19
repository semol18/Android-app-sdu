package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IStockAPI
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class StockAPITest {

    @Test
    fun searchStockThatDoesNotExist() {
        var api: IStockAPI = StockAPI();
        val result = api.searchStock("AAPLE");
        assertFalse(result.getResult())
    }

    @Test
    fun searchStockThatExists() {
        var api: IStockAPI = StockAPI();
        val result = api.searchStock("AAPL");
        assertTrue(result.getResult())
        assertNotNull(result.getStock())
    }

    @Test
    fun updateStock() {
        /*var api: IStockAPI = StockAPI();
        val stock = api.updateStock("GME");
        assert(stock.getSymbol() == "GME");
        assert(stock.getPrice().getPrice() != 0.0);
        assert(stock.getPrice().getTimeStamp().before(Date()));*/
    }
}