package dk.sdu.stocktracker.ui.stockdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.api.IPrice

class StockDetailFragment : Fragment() {

    companion object {
        fun newInstance() = StockDetailFragment()
    }

    private lateinit var viewModel: StockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(StockViewModel::class.java)

        val root = inflater.inflate(R.layout.stock_detail_fragment, container, false)

        val stockName: TextView = root.findViewById(R.id.ticketName);
        val priceText: TextView = root.findViewById(R.id.valueText);
        val timestamp: TextView = root.findViewById(R.id.updateTimeStamp);
        val priceDiff: TextView = root.findViewById(R.id.differenceText);

            viewModel.getStock().observe(viewLifecycleOwner, {
                stockName.text = it.getName();

                val price: IPrice = it.getPrice();
                priceText.text = price.getPrice().toString();
                timestamp.text = price.getTimeStamp().toString();
                priceDiff.text = price.getChange().toString()
            });

        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}