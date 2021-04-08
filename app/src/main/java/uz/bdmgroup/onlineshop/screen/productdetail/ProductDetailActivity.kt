package uz.bdmgroup.onlineshop.screen.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.model.ProductModel
import uz.bdmgroup.onlineshop.utils.Constants
import uz.bdmgroup.onlineshop.utils.PrefUtils

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        cardViewBack.setOnClickListener {
            finish()
        }

        cardViewFavorite.setOnClickListener {
            PrefUtils.setFavorite(item)

            if (PrefUtils.checkFavorite(item)){
                imgFavorite.setImageResource(R.drawable.ic_heart)
            }else{
                imgFavorite.setImageResource(R.drawable.ic_love)
            }

        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        tvTitle.text = item.name
        tvProductName.text = item.name
        tvProductPrice.text = item.price

        if (PrefUtils.getCartCount(item) > 0){
            btnAdd2Cart.visibility = View.GONE
        }

        if (PrefUtils.checkFavorite(item)){
            imgFavorite.setImageResource(R.drawable.ic_heart)
        }else{
            imgFavorite.setImageResource(R.drawable.ic_love)
        }

        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(imgProduct)

        btnAdd2Cart.setOnClickListener {
            item.cartCount = 1
            PrefUtils.setCart(item)
            Toast.makeText(this, "Product added to cart!", Toast.LENGTH_LONG).show()
            finish()
        }

    }
}