package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStockAPI
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class StockAPI private constructor() : IStockAPI {
    private val host: String = "https://finnhub.io/api/v1/";

    companion object {
        private lateinit var INSTANCE: StockAPI;
        fun getInstance(): StockAPI {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = StockAPI();
            }
            return INSTANCE
        }
    }

    override fun searchStock(searchString: String): Array<Stock> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(this.host + "search?q=" + searchString + "&token=c2io8niad3i8gi7pplog")
            .get()
            .build()

        val response = client.newCall(request).execute()

        val bodyString = response.body!!.string();

        val json = JSONObject(bodyString);

        val count = json.get("count").toString().toInt();

        val array : ArrayList<Stock> = ArrayList();

        val results = json.getJSONArray("result");

        for (i in 0 until count) {

            val stock = results.getJSONObject(i);
            val symbol = stock.get("symbol").toString();
            val name = stock.get("description").toString()
            array.add(Stock(symbol, name));
        }

        return array.toTypedArray();
    }

    override fun getPrice(symbol: String): Flow<IPrice?> {
        val job = Job();
        val scope = CoroutineScope(Dispatchers.IO + job);

        val price: MutableStateFlow<IPrice?> = MutableStateFlow(null);

        scope.launch {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(host + "quote?symbol=" + symbol + "&token=sandbox_c2io8niad3i8gi7pplp0")
                .get()
                .build()

            val response = client.newCall(request).execute()

            val bodyString = response.body!!.string();

            val json = JSONObject(bodyString);

            val current = json.get("c").toString();

            val open = json.get("pc").toString();

            val changed = current.toDouble() - open.toDouble();

            val time = json.get("t").toString();

            val timeStamp = Date(time.toLong()*1000)

            price.emit(Price(current.toDouble(), changed, timeStamp));
        }

        return price;
    }

    private fun stockDetail(symbol: String): Stock {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://yahoo-finance-low-latency.p.rapidapi.com/v11/finance/quoteSummary/"+ symbol + "?modules=price")
            .get()
            .addHeader("x-rapidapi-key", "862cf62549mshcf32c36ff955a33p1dd105jsna2416080318a")
            .addHeader("x-rapidapi-host", "yahoo-finance-low-latency.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()

        val bodyString = response.body!!.string();

        val json = JSONObject(bodyString);

        val quote = json.getJSONObject("quoteSummary");
        var result = quote.getJSONArray("result").getJSONObject(0);
        result = result.getJSONObject("price");

        val regularMarketPrice = getRawValue(result,"regularMarketPrice");
        val regularMarketChange = getRawValue(result,"regularMarketChange");

        val regularMarketTimeStamp = result.get("regularMarketTime").toString().toLong();
        val regularMarketTime = Date(regularMarketTimeStamp*1000);

        val fetchedSymbol = result.get("symbol").toString();
        val longName = result.get("longName").toString();

        return Stock(fetchedSymbol, longName);

    }

    private fun getRawValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("raw").toString();
    }

    private fun getFormattedValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("fmt").toString();
    }

}