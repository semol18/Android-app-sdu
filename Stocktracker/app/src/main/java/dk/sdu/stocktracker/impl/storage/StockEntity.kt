package dk.sdu.stocktracker.impl.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class StockEntity (
    @PrimaryKey val symbol: String
)