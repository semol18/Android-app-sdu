package dk.sdu.stocktracker.impl.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

class StockEntity {

    @Entity(tableName = "stock")
    data class Stock (
    @PrimaryKey val symbol: String
    )
}