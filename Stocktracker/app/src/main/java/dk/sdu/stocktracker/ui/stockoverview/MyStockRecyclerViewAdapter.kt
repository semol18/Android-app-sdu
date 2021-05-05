package dk.sdu.stocktracker.ui.stockoverview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dk.sdu.stocktracker.R
import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStock

/**
 * [RecyclerView.Adapter] that can display a [IStock].
 * TODO: Replace the implementation with code for your data type.
 */
class MyStockRecyclerViewAdapter(private val dataSet: Array<IStock>,
                                 private val onClick: (stock: IStock) -> Unit) :
    RecyclerView.Adapter<MyStockRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, val onClick: (stock: IStock) -> Unit) : RecyclerView.ViewHolder(view) {
        val symbol: TextView = view.findViewById(R.id.list_symbol);
        val price: TextView = view.findViewById(R.id.list_price);
        val difference: TextView = view.findViewById(R.id.list_change);
        val timestamp: TextView = view.findViewById(R.id.list_timestamp);

        var currentStock: IStock? = null;

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener{
                currentStock?.let {
                    this.onClick(it) }
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.stock_overview_list_item, viewGroup, false)

        return ViewHolder(view, this.onClick)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val stock: IStock = dataSet[position];
        val price: IPrice = stock.getPrice();

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.symbol.text = stock.getSymbol();
        viewHolder.timestamp.text = price.getTimeStamp().toString();
        viewHolder.price.text = price.getPrice().toString();
        viewHolder.difference.text = price.getChange().toString();

        viewHolder.currentStock = stock;
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}