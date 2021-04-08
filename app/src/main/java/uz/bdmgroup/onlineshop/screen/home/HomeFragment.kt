package uz.bdmgroup.onlineshop.screen.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.model.CategoryModel
import uz.bdmgroup.onlineshop.model.OfferModel
import uz.bdmgroup.onlineshop.screen.MainViewModel
import uz.bdmgroup.onlineshop.utils.Constants
import uz.bdmgroup.onlineshop.view.CategoryAdapter
import uz.bdmgroup.onlineshop.view.CategoryAdapterCallback
import uz.bdmgroup.onlineshop.view.ProductAdapter


class HomeFragment : Fragment() {
    var offers: List<OfferModel> = emptyList()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            loadData()
        }

        recyclerCategories.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        recyclerProducts.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(requireActivity(), Observer {
            swipe.isRefreshing = it
        })

        viewModel.offersData.observe(requireActivity(), Observer {
            carouselView.setImageListener { position, imageView ->
                Glide.with(imageView).load(Constants.HOST_IMAGE + it[position].image).into(imageView)
            }

            carouselView.pageCount = it.count()
        })

        viewModel.categoriesData.observe(requireActivity(), Observer {
            recyclerCategories.adapter = CategoryAdapter(it, object: CategoryAdapterCallback{
                override fun onClickItem(item: CategoryModel) {
                    viewModel.getProductsByCategory(item.id)
                }
            })
        })

        viewModel.productsData.observe(requireActivity(), Observer {
            recyclerProducts.adapter = ProductAdapter(it)
        })

        loadData()
    }

    fun loadData(){
        viewModel.getOffers()
        viewModel.getAllDBCategories()
        viewModel.getAllDBProducts()
//        viewModel.getTopProducts()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}