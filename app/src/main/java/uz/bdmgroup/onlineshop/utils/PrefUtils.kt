package uz.bdmgroup.onlineshop.utils

import com.orhanobut.hawk.Hawk
import uz.bdmgroup.onlineshop.model.CartModel
import uz.bdmgroup.onlineshop.model.ProductModel

object PrefUtils {
    const val PREF_FAVORITES = "pref_favorites"
    const val PREF_CART = "pref_cart"
    const val PREF_TOKEN= "pref_token"
    const val PREF_FCM_TOKEN= "pref_fcm_token"

    fun setFavorite(item: ProductModel){
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null){
            items.remove(item.id)
        }else{
            items.add(item.id)
        }

        Hawk.put(PREF_FAVORITES, items)

    }

    fun getFavoriteList(): ArrayList<Int>{
        return Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
    }

    fun checkFavorite(item: ProductModel): Boolean{
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }

    fun setCart(item: ProductModel){
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf<CartModel>())
        val cart = items.filter { it.id == item.id }.firstOrNull()
        if (cart != null){
            if (item.cartCount > 0){
                cart.count = item.cartCount
            }else{
                items.remove(cart)
            }
        }else{
            val newCart = CartModel(item.id, item.cartCount)
            items.add(newCart)
        }

        Hawk.put(PREF_CART, items)
    }

    fun getCartList(): ArrayList<CartModel>{
        return Hawk.get(PREF_CART, arrayListOf<CartModel>())
    }

    fun getCartCount(item: ProductModel): Int{
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf<CartModel>())
        return items.filter { it.id == item.id }.firstOrNull()?.count ?: 0
    }

    fun setToken(value: String){
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String{
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setFCMToken(value: String){
        Hawk.put(PREF_FCM_TOKEN, value)
    }

    fun getFCMToken(): String{
        return Hawk.get(PREF_FCM_TOKEN, "")
    }
}