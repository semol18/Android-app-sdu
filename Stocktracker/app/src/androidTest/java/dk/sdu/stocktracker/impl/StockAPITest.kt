package dk.sdu.stocktracker.impl

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.junit.Test

import java.util.*

class StockAPITest {

    @Test
    fun searchStock() {
        assert(StockAPI.getInstance().searchStock("GameStop")[0].symbol == "GME");
    }

    @Test
    fun getPrice() = runBlocking {
        val price = StockAPI.getInstance().getPrice("GME");

        try {
            runBlocking {
                price.collect {
                    if (it != null) {
                        assert(it.getTimeStamp().before(Date()));
                        assert(it.getPrice() > 0.0);
                        this.coroutineContext.job.cancelAndJoin();
                    }
                }
            }
        } catch (ex: Exception) {
            assert(true);
        }
    }
}