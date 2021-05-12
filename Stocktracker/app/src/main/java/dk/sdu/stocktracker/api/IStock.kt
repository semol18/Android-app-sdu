package dk.sdu.stocktracker.api

interface IStock {
    fun getPrice(): IPrice;
    fun getSymbol(): String;
    fun getName(): String;
}