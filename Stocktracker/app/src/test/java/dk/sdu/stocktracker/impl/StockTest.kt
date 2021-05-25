package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStockAPI
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test

class StockTest {

    private lateinit var testee: Stock;

    @Before
    fun setUp() {
        testee = Stock("Test", "Test")
    }

    @Test
    fun getPrice() {
        val stockAPI: IStockAPI = mockk();

        every {stockAPI.getPrice(any())} returns MutableStateFlow<IPrice?>(null);

        testee.getPrice(stockAPI);

        verify(exactly = 1) { stockAPI.getPrice(testee.symbol) }
    }
}