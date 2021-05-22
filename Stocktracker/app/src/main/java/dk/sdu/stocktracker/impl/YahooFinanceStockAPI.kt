package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.ISearchStockResult
import dk.sdu.stocktracker.api.IStockAPI
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.lang.RuntimeException
import java.util.*

class YahooFinanceStockAPI private constructor() /*: IStockAPI*/ {
    /*
    private val host: String = "https://yahoo-finance-low-latency.p.rapidapi.com/v11/finance/quoteSummary/";

    companion object {
        private lateinit var INSTANCE: YahooFinanceStockAPI;
        fun getInstance(): YahooFinanceStockAPI {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = YahooFinanceStockAPI();
            }
            return INSTANCE
        }
    }

    override fun searchStock(searchString: String): ISearchStockResult {
        val noStockFoundResult = object: ISearchStockResult{
            override fun getResult(): Boolean {
                return false;
            }

            override fun getStock(): Stock {
                throw RuntimeException("No stock found");
            }

        };

        try {
            val stock: Stock = stockDetail(searchString);
            if (stock == null) {
                return noStockFoundResult;
            }
            return object: ISearchStockResult{
                override fun getResult(): Boolean {
                    return true;
                }

                override fun getStock(): Stock {
                    return stock;
                }

            };
        } catch (ex: JSONException) {
            return noStockFoundResult;
        }
    }

    override fun updateStock(stock: Stock): Stock {
        val fifteenMinutesDelay = Date(Date().time.minus(1000 * 60 * 15));
        if (stock.getPrice(this).getTimeStamp().before(fifteenMinutesDelay)) {
            return stockDetail(stock.symbol);
        }
        return stock;
    }

    override fun getPrice(symbol: String): IPrice {
        TODO("Not yet implemented")
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

        return Stock(fetchedSymbol, longName);

    }

    private fun getRawValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("raw").toString();
    }

    private fun getFormattedValue(json: JSONObject, name: String) : String {
        return json.getJSONObject(name).get("fmt").toString();
    }
*/
}