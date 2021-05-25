package dk.sdu.stocktracker.ui.stockdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.storage.IStockStorage
import dk.sdu.stocktracker.databinding.StockDetailFragmentBinding
import dk.sdu.stocktracker.impl.StockAPI
import dk.sdu.stocktracker.impl.storage.LocalStockStorage
import dk.sdu.stocktracker.ui.stockoverview.StockOverviewFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StockDetailFragment : Fragment() {

    companion object {
        fun newInstance() = StockDetailFragment()
    }

    private lateinit var binding: StockDetailFragmentBinding
    private lateinit var viewModel: StockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)

        binding = StockDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val stockName: TextView = binding.ticketName
        val priceText: TextView = binding.valueText
        val timestamp: TextView = binding.updateTimeStamp
        val priceDiff: TextView = binding.differenceText

        viewModel.getStock().observe(viewLifecycleOwner, { stock ->
            if (stock != null) {

                stockName.text = stock?.name

                val job = Job();
                val scope = CoroutineScope(Dispatchers.Main + job);

                val price: Flow<IPrice?> = stock.getPrice(StockAPI.getInstance());
                scope.launch {
                    price.collect {
                        if (it != null) {
                            priceText.text = it.getPrice().toString();
                            timestamp.text = it.getTimeStamp().toString();
                            priceDiff.text = it.getChange().toString()
                        }
                    }
                }
            }
        });

        val removeStockButton: Button = binding.buttonRemoveStock;
        removeStockButton.setOnClickListener{
            val stockStorage : IStockStorage = LocalStockStorage.getInstance(requireContext());
            val stock = viewModel.getStock().value;
            if (stock != null) {
                stockStorage.deleteStock(stock);

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, StockOverviewFragment.newInstance())
                    ?.setReorderingAllowed(true)
                    ?.commit()
            }
        }

        return view
    }

}