package dk.sdu.stocktracker.ui.stocksearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.ui.stockoverview.StockOverviewFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchStockViewModel::class.java)
        var button: Button = root.findViewById(R.id.searchStockButton);
        var textEdit: EditText = root.findViewById(R.id.editTextSymbol);
        button.setOnClickListener{

            var handler: Handler = object: Handler(Looper.getMainLooper()){
                @Override
                override fun handleMessage(msg: Message) {
                    if (msg.what == 1) {
                        onSearchReturn(true);
                    } else {
                        onSearchReturn(false);
                    }
                }
            }

            Thread{
                viewModel.onSearch(textEdit.text.toString(), handler)
            }.start();
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

}