package dk.sdu.stocktracker.ui.stocksearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.impl.storage.LocalStockStorage
import dk.sdu.stocktracker.ui.stockoverview.StockOverviewFragment


class SearchStockFragment : Fragment() {

    companion object {
        fun newInstance() = SearchStockFragment()
    }

    private lateinit var viewModel: SearchStockViewModel
    private lateinit var root: View;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.root = inflater.inflate(R.layout.search_stock_fragment, container, false);

        val adapter =
            SearchStockRecyclerViewAdapter(emptyArray()) { stock: Stock ->
                onAdapterClicked(stock)
            };

        val list: RecyclerView = root.findViewById(R.id.searchStockList);

        list.adapter = adapter;

        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SearchStockViewModel::class.java);
        var button: Button = root.findViewById(R.id.searchStockButton);
        var textEdit: EditText = root.findViewById(R.id.editTextSymbol);
        button.setOnClickListener {
            viewModel.onSearch(textEdit.text.toString());
        }

        viewModel.getStocks().observe(viewLifecycleOwner) {
            val adapter =
                SearchStockRecyclerViewAdapter(it) { stock: Stock ->
                    onAdapterClicked(stock)
                };

            val list: RecyclerView = root.findViewById(R.id.searchStockList);

            list.adapter = adapter;
        }
    }

    private fun onSearchReturn(result: Boolean) {
        if (result) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, StockOverviewFragment.newInstance())
                ?.setReorderingAllowed(true)
                ?.commit()
        } else {
            var errorTextView: TextView = root.findViewById(R.id.searchErrorTextView);
            errorTextView.text = "Could not find any stock information on the provided symbol"
        }
    }

    private fun onAdapterClicked(stock: Stock) {
        LocalStockStorage.getInstance(requireContext()).saveStock(stock);

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, StockOverviewFragment.newInstance())
            ?.setReorderingAllowed(true)
            ?.commit()
    }

}