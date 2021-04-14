package uz.bdmgroup.onlineshop.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.bdmgroup.onlineshop.utils.Constants

object NetworkManager{
    var retrofit: Retrofit? = null

    var api: Api? = null

    fun getApiService(): Api{
        if (api == null){
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(AppInterceptor())

            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
            api = retrofit!!.create(Api::class.java)
        }

        return api!!
    }
}