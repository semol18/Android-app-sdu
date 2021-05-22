package dk.sdu.stocktracker.ui.stocksearch

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dk.sdu.stocktracker.api.IStockAPI
import dk.sdu.stocktracker.api.storage.IStockStorage
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.impl.StockAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchStockViewModel : ViewModel() {
    private val stockAPI: IStockAPI = StockAPI.getInstance();
    private val stocks = MutableLiveData<Array<Stock>>();

    fun onSearch(text: String) {
        val job = Job();
        val scope = CoroutineScope(Dispatchers.IO + job);

        scope.launch {
            stocks.postValue(stockAPI.searchStock(text));
        }
    }

    fun getStocks(): LiveData<Array<Stock>> {
        return stocks;
    }
}