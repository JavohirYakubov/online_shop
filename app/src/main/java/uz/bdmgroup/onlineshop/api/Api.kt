package uz.bdmgroup.onlineshop.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.bdmgroup.onlineshop.model.BaseResponse
import uz.bdmgroup.onlineshop.model.CategoryModel
import uz.bdmgroup.onlineshop.model.OfferModel
import uz.bdmgroup.onlineshop.model.ProductModel
import uz.bdmgroup.onlineshop.model.request.GetProductsByIdsRequest

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest): Observable<BaseResponse<List<ProductModel>>>

}