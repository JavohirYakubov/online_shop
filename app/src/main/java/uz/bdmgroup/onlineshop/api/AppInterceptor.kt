package uz.bdmgroup.onlineshop.api

import okhttp3.Interceptor
import okhttp3.Response
import uz.bdmgroup.onlineshop.utils.PrefUtils

class AppInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (!PrefUtils.getToken().isNullOrEmpty()){
            original.newBuilder().addHeader("token", PrefUtils.getToken())
        }
        return chain.proceed(original)
    }

}