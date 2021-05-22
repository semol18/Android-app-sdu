package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import java.util.*

class Price(private val price: Double, private val change: Double, private val timeStamp: Date) : IPrice {

    override fun getPrice(): Double {
        return this.price;
    }

    override fun getTimeStamp(): Date {
        return this.timeStamp;
    }

    override fun getChange(): Double {
        return this.change;
    }
}