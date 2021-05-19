package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.ISearchStockResult
import dk.sdu.stocktracker.api.IStock
import dk.sdu.stocktracker.api.IStockAPI
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.lang.RuntimeException
import java.util.*

class StockAPI : IStockAPI {
    private val host: String = "https://yahoo-finance-low-latency.p.rapidapi.com/v11/finance/quoteSummary/";

    override fun searchStock(searchString: String): ISearchStockResult {
        val noStockFoundResult = object: ISearchStockResult{
            override fun getResult(): Boolean {
                return false;
            }

            override fun getStock(): IStock {
                throw RuntimeException("No stock found");
            }

        };

        try {
            val stock: IStock = stockDetail(searchString);
            if (stock == null) {
                return noStockFoundResult;
            }
            return object: ISearchStockResult{
                override fun getResult(): Boolean {
                    return true;
                }

                override fun getStock(): IStock {
                    return stock;
                }

            };
        } catch (ex: JSONException) {
            return noStockFoundResult;
        }
    }

    override fun updateStock(stock: IStock): IStock {
        val fifteenMinutesDelay = Date(Date().time.minus(1000 * 60 * 15));
        if (stock.getPrice().getTimeStamp().before(fifteenMinutesDelay)) {
            return stockDetail(stock.getSymbol());
        }
        return stock;
    }

    private  fun stockDetail(symbol: String): IStock {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://yahoo-finance-low-latency.p.rapidapi.com/v11/finance/quoteSummary/"+ symbol + "?modules=price")
            .get()
            .addHeader("x-rapidapi-key", "862cf62549mshcf32c36ff955a33p1dd105jsna2416080318a")
            .addHeader("x-rapidapi-host", "yahoo-finance-low-latency.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()

        val json = JSONObject(response.body!!.string());

        val quote = json.getJSONObject("quoteSummary");
        var result = quote.getJSONArray("result").getJSONObject(0);
        result = result.getJSONObject("price");

        val regularMarketPrice = getRawValue(result,"regularMarketPrice");
        val regularMarketChange = getRawValue(result,"regularMarketChange");

        val regularMarketTimeStamp = result.get("regularMarketTime").toString().toLong();
        val regularMarketTime = Date(regularMarketTimeStamp*1000);

        val fetchedSymbol = result.get("symbol").toString();
        val longName = result.get("longName").toString();

        val price: IPrice = object: IPrice {
            override fun getPrice(): Double {
                return regularMarketPrice.toDouble();
            }

            override fun getTimeStamp(): Date {
                return regularMarketTime;
            }

            override fun getChange(): Double {
                return regularMarketChange.toDouble();
            }
        }

        return Stock(fetchedSymbol, longName, price);

    }

    private fun getRawValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("raw").toString();
    }

    private fun getFormattedValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("fmt").toString();
    }

}