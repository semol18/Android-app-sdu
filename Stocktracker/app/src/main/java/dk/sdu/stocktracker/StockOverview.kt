package dk.sdu.stocktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.sdu.stocktracker.databinding.StockOverviewActivityBinding
import dk.sdu.stocktracker.ui.stockoverview.StockOverviewFragment

class StockOverview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_overview_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StockOverviewFragment.newInstance())
                .commitNow()
        }
    }
}