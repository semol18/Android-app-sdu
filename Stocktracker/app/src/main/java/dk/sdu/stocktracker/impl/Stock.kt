package dk.sdu.stocktracker.impl

import dk.sdu.stocktracker.api.IPrice
import dk.sdu.stocktracker.api.IStock

class Stock : IStock {
    private val _symbol: String;
    private val _name: String;
    private val _price: IPrice;

    constructor(symbol: String, name: String, price: IPrice) {
        this._symbol = symbol;
        this._name = name;
        this._price = price;
    }

    override fun getPrice(): IPrice {
        return this._price;
    }

    override fun getSymbol(): String {
        return this._symbol;
    }

    override fun getName(): String {
        return this._name;
    }
}