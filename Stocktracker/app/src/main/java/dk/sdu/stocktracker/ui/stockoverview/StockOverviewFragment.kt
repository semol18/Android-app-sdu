package dk.sdu.stocktracker.ui.stockoverview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.impl.storage.LocalStockStorage
import dk.sdu.stocktracker.ui.stockdetail.StockDetailFragment
import dk.sdu.stocktracker.ui.stockdetail.StockViewModel
import dk.sdu.stocktracker.ui.stocksearch.SearchStockFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class StockOverviewFragment : Fragment() {

    companion object {
        fun newInstance() = StockOverviewFragment()
    }

    private lateinit var viewModel: StockOverviewViewModel
    private lateinit var root: View;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.stock_overview_fragment, container, false);

        this.root = root;

        val button: Button = root.findViewById(R.id.addStock);

        button.setOnClickListener { onButtonClicked() }

        val stockFlow: Flow<Array<Stock>> = LocalStockStorage.getInstance(requireContext()).getAllStock();
        updateStockRecyclerViewAdapter(stockFlow);

        return root;
    }

    private fun updateStockRecyclerViewAdapter(stockFlow: Flow<Array<Stock>>) {
        val job = Job();
        val scope = CoroutineScope(Dispatchers.Main + job);

        scope.launch {
            stockFlow.collect {
                val adapter =
                    StockRecyclerViewAdapter(it) { stock: Stock ->
                        onAdapterClicked(stock)
                    };

                val list: RecyclerView = root.findViewById(R.id.stockList);

                list.adapter = adapter;
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, StockOverviewViewModelFactory(LocalStockStorage.getInstance(requireContext()))).get(
            StockOverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun onAdapterClicked(stock: Stock) {
        val viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java);
        viewModel.setStock(stock);

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, StockDetailFragment.newInstance())
            ?.setReorderingAllowed(true)
            ?.addToBackStack("StockOverview")
            ?.commit()
    }

    private fun onButtonClicked() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, SearchStockFragment.newInstance())
            ?.setReorderingAllowed(true)
            ?.addToBackStack("StockOverview")
            ?.commit()
    }

}