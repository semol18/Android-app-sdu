package dk.sdu.stocktracker.ui.stockoverview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStock
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.ui.stockdetail.StockDetailFragment
import dk.sdu.stocktracker.ui.stockdetail.StockViewModel
import java.util.*

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

        val tempPrice: IPrice = object:
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
        }

        val array: Array<IStock> = arrayOf(Stock("\$GME", "Test", tempPrice), Stock("\$GME2", "Test2", tempPrice), Stock("\$GME3", "Test3", tempPrice));

        val adapter: MyStockRecyclerViewAdapter = MyStockRecyclerViewAdapter(array) { stock ->
            onAdapterClicked(stock)
        };

        val list: RecyclerView = root.findViewById(R.id.stockList);

        list.adapter = adapter;

        this.root = root;


        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StockOverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun onAdapterClicked(stock: IStock) {
        val viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java);
        viewModel.setStock(stock);

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, StockDetailFragment.newInstance())
            ?.setReorderingAllowed(true)
            ?.addToBackStack("StockOverview")
            ?.commit()
    }

}