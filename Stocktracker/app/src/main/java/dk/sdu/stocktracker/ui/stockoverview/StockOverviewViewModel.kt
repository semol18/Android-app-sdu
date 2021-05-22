package dk.sdu.stocktracker.ui.stockoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dk.sdu.stocktracker.api.storage.IStockStorage
import dk.sdu.stocktracker.impl.Stock
import kotlinx.coroutines.flow.Flow

class StockOverviewViewModel(private val localStockStorage: IStockStorage) : ViewModel() {
    val array: Flow<Array<Stock>> = localStockStorage.getAllStock();
}

class StockOverviewViewModelFactory(private val localStockStorage: IStockStorage) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StockOverviewViewModel(localStockStorage) as T
    }
}