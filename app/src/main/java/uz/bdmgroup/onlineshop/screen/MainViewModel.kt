package uz.bdmgroup.onlineshop.screen

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.bdmgroup.onlineshop.api.Api
import uz.bdmgroup.onlineshop.api.NetworkManager
import uz.bdmgroup.onlineshop.api.repository.ShopRepository
import uz.bdmgroup.onlineshop.db.AppDatabese
import uz.bdmgroup.onlineshop.model.BaseResponse
import uz.bdmgroup.onlineshop.model.CategoryModel
import uz.bdmgroup.onlineshop.model.OfferModel
import uz.bdmgroup.onlineshop.model.ProductModel
import uz.bdmgroup.onlineshop.utils.Constants
import uz.bdmgroup.onlineshop.view.CategoryAdapter

class MainViewModel: ViewModel() {
    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()


    fun getOffers(){
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories(){
        repository.getCategories(error, categoriesData)
    }

    fun getTopProducts(){
        repository.getTopProducts(error, productsData)
    }

    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id, error, productsData)
    }

    fun getProductsByIds(ids: List<Int>){
        repository.getProductsByIds(ids, error, progress, productsData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabese.getDatabase().getProductDao().deleteAll()
            AppDatabese.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabese.getDatabase().getCategoryDao().deleteAll()
            AppDatabese.getDatabase().getCategoryDao().insetAll(items)
        }
    }

    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch{
            productsData.value = withContext(Dispatchers.IO){AppDatabese.getDatabase().getProductDao().getAllProducts()}
        }

    }

    fun getAllDBCategories(){
        CoroutineScope(Dispatchers.Main).launch{
            categoriesData.value = withContext(Dispatchers.IO){AppDatabese.getDatabase().getCategoryDao().getAllCategories()}
        }

    }
}