package uz.bdmgroup.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.bdmgroup.onlineshop.model.CategoryModel
import uz.bdmgroup.onlineshop.model.ProductModel

@Dao
interface ProductDao {

    @Query("DELETE from products")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductModel>
}