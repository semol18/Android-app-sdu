package dk.sdu.stocktracker.ui.stockdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.StockOverview
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.databinding.StockDetailFragmentBinding

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

            viewModel.getStock().observe(viewLifecycleOwner, {
                stockName.text = it.getName();

                val price: IPrice = it.getPrice();
                priceText.text = price.getPrice().toString();
                timestamp.text = price.getTimeStamp().toString();
                priceDiff.text = price.getChange().toString()
            });
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}