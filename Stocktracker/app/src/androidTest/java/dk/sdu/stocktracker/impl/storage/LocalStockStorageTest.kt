package dk.sdu.stocktracker.impl.storage

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dk.sdu.stocktracker.api.IStock
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

    private fun createStock(symbol: String) : IStock {
        var stock = mockk<IStock>();
        every {stock.getSymbol()} returns symbol;
        return stock;
    }

    private fun daoContainsStock(stock: IStock) : Boolean {
        var stocks = localStorage.stockDao().getStocks();

        return stocks[0].symbol == stock.getSymbol();
    }

    private fun daoContainsNoStock() : Boolean {
        var stocks = localStorage.stockDao().getStocks();

        return stocks.contentEquals(emptyArray());
    }
}