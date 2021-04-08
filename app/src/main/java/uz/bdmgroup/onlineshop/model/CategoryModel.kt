package uz.bdmgroup.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryModel(
        @PrimaryKey(autoGenerate = true)
        val uid: Long = 0,
        val id: Int,
        val title: String,
        val icon: String,
        var checked: Boolean = false
)