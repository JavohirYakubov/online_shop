package uz.bdmgroup.onlineshop.model

import java.io.Serializable

data class CartModel(
    val id: Int,
    var count: Int
): Serializable