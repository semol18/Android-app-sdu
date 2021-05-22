package dk.sdu.stocktracker.ui.stockdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.stocktracker.impl.Stock

class StockViewModel : ViewModel() {
    private var stock: MutableLiveData<Stock?> = MutableLiveData(null);

    fun getStock() : LiveData<Stock?> {
        return this.stock;
    }

    fun setStock(stock: Stock) {
        this.stock.value = stock;
    }
}