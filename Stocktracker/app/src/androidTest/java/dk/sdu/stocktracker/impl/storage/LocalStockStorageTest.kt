package dk.sdu.stocktracker.impl.storage

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dk.sdu.stocktracker.impl.Stock
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalStockStorageTest {

    private lateinit var localStorage: LocalStockStorage

    @Before
    fun setUp() {
        localStorage = LocalStockStorage.getInstance(getInstrumentation().targetContext.applicationContext);
    }

    @After
    fun tearDown() {
        localStorage.stockDao().getStocks().forEach { stock ->
            run {
                localStorage.stockDao().removeStock(stock);
            }
        }
    }

    @Test
    fun saveStock() {
        var stock = createStock("GME");

        localStorage.saveStock(stock);

        assert(daoContainsStock(stock));
    }

    @Test
    fun deleteStock() {
        var stock = createStock("GME");

        localStorage.saveStock(stock);

        assert(daoContainsStock(stock));

        localStorage.deleteStock(stock);

        assert(daoContainsNoStock());
    }

    @Test
    fun preventDuplicatedStock() {
        var stock = createStock("GME");

        localStorage.saveStock(stock);

        assert(daoContainsStock(stock));

        localStorage.saveStock(stock);

        assert(localStorage.stockDao().getStocks().size === 1);
    }

    @Test
    fun deleteNonExistingStock() {
        var stock = createStock("GME");

        localStorage.saveStock(stock);

        assert(daoContainsStock(stock));

        var nonExistingStock = createStock("noGME");

        localStorage.deleteStock(nonExistingStock);
    }

    private fun createStock(symbol: String) : Stock {
        var stock = mockk<Stock>();
        every {stock.symbol} returns symbol;
        every {stock.name} returns symbol;
        return stock;
    }

    private fun daoContainsStock(stock: Stock) : Boolean {
        var stocks = localStorage.stockDao().getStocks();

        return stocks[0].symbol == stock.symbol;
    }

    private fun daoContainsNoStock() : Boolean {
        var stocks = localStorage.stockDao().getStocks();

        return stocks.contentEquals(emptyArray());
    }
}