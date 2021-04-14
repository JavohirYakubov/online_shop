package uz.bdmgroup.onlineshop.model.request

data class RegisterRequest(
    val fullname: String,
    val phone: String,
    val password: String
)