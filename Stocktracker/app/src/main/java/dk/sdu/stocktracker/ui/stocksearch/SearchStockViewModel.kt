package dk.sdu.stocktracker.ui.stocksearch

import android.os.Handler
import androidx.lifecycle.ViewModel
import dk.sdu.stocktracker.api.ILocalStockStorage
import dk.sdu.stocktracker.api.IStockAPI
import dk.sdu.stocktracker.impl.LocalStockStorage
import dk.sdu.stocktracker.impl.StockAPI

class SearchStockViewModel : ViewModel() {
    private val stockAPI : IStockAPI = StockAPI();
    private val localStockStorage: ILocalStockStorage = LocalStockStorage();

    fun onSearch(text: String, handler: Handler) {
        val result = stockAPI.searchStock(text);

        if (result.getResult()) {
            //localStockStorage.saveStock(result.getStock());
            handler.sendEmptyMessage(1);
        }

        handler.sendEmptyMessage(0);
    }
}