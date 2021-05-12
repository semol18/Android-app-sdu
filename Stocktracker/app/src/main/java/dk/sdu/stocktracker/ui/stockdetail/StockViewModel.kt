package dk.sdu.stocktracker.ui.stockdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStock
import dk.sdu.stocktracker.impl.Stock
import java.util.*

class StockViewModel : ViewModel() {
    private val stock: MutableLiveData<IStock> = MutableLiveData(Stock("\$GME", "GameStop Corp.", object:
        IPrice {
        override fun getPrice(): Double {
            return 160.30;
        }

        override fun getTimeStamp(): Date {
            return Date();
        }

        override fun getChange(): Double {
            return 4.01;
        }
    }));

    constructor() {
        //TODO: add api call
    }

    fun getStock() : LiveData<IStock> {
        return this.stock;
    }

    fun setStock(stock: IStock) {
        this.stock.value = stock;
    }

    //TODO add web call to Yahoo Finance for data


}