package dk.sdu.stocktracker.ui.stocksearch

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.impl.Stock
import dk.sdu.stocktracker.impl.StockAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * [RecyclerView.Adapter] that can display a [Stock].
 * TODO: Replace the implementation with code for your data type.
 */
class SearchStockRecyclerViewAdapter(
    private val dataSet: Array<Stock>,
    private val onClick: (stock: Stock) -> Unit
) :
    RecyclerView.Adapter<SearchStockRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, val onClick: (stock: Stock) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val symbol: TextView = view.findViewById(R.id.search_list_symbol);
        val name: TextView = view.findViewById(R.id.search_list_name);

        var currentStock: Stock? = null;

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener {
                currentStock?.let {
                    this.onClick(it)
                }
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.stock_search_list_item, viewGroup, false)

        return ViewHolder(view, this.onClick)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val stock: Stock = dataSet[position];

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.symbol.text = stock.symbol;
        viewHolder.name.text = stock.name;

        viewHolder.currentStock = stock;
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}