package dk.sdu.stocktracker.api

import java.util.*

interface IPrice {
    fun getPrice() : Double;
    fun getTimeStamp() : Date;
    fun getChange(): Double;
}