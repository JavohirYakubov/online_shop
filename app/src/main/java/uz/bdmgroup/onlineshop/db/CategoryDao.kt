package uz.bdmgroup.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.bdmgroup.onlineshop.model.CategoryModel

@Dao
interface CategoryDao{

    @Query("DELETE from categories")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(items: List<CategoryModel>)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryModel>
}