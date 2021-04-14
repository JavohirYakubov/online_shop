package uz.bdmgroup.onlineshop.screen.makeorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_make_order.*
import kotlinx.android.synthetic.main.activity_make_order.flProgress
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.bdmgroup.onlineshop.MapsActivity
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.model.AddressModel
import uz.bdmgroup.onlineshop.model.CartModel
import uz.bdmgroup.onlineshop.model.ProductModel
import uz.bdmgroup.onlineshop.screen.MainViewModel
import uz.bdmgroup.onlineshop.utils.Constants

class MakeOrderActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    var address: AddressModel? = null
    lateinit var items: List<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })


        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        tvTotalAmount.setText(items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0) }.toString())

        edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btnMakeOrder.setOnClickListener {
            viewModel.makeOrder(items.map { CartModel(it.id, it.cartCount) }, address?.latitude ?: 0.0, address?.longitude ?: 0.0, edComment.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        edAddress.setText("${address.latitude}, ${address.longitude}")
    }
}