package uz.bdmgroup.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class ProductModel(
        @PrimaryKey(autoGenerate = true)
        val uid: Long = 0,
        val id: Int,
        val name: String,
        val price: String,
        val image: String,
        var cartCount: Int
) : Serializable