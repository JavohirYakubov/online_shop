package uz.bdmgroup.onlineshop.model.request

data class GetProductsByIdsRequest(
    val products: List<Int>
)