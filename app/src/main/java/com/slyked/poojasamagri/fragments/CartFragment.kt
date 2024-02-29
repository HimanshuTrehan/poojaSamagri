package com.slyked.poojasamagri.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.slyked.admin.api.ResponseData
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.viewmodelfactory.CartViewModelFactory
import com.slyked.poojasamagri.AppConfiguration
import com.slyked.poojasamagri.cart.CartAdapter
import com.slyked.poojasamagri.cart.CartViewModel
import com.slyked.poojasamagri.databinding.FragmentCartBinding
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.ui.OrderSummary
import com.slyked.poojasamagri.utils.CommonMethods
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(),CartAdapter.CartListener {
    lateinit var binding: FragmentCartBinding
    @Inject
    lateinit var cartProductDao: CartProductDao
    lateinit var cartViewModel: CartViewModel
    lateinit var adapter: CartAdapter
     var cartList: List<CartProduct> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false);
        setupViewModel()
        getCartData()

        if (!AppConfiguration.store_status){
            binding.checkoutBtn.setBackgroundColor(Color.GRAY)
        }

        binding.checkoutBtn.setOnClickListener {
            if (!AppConfiguration.store_status) {
                CommonMethods.toastMessage(requireContext(),"Store is Close Now")
            }else {
                val intent = Intent(requireContext(), OrderSummary::class.java)
                startActivity(intent)
            }
        }
        return binding.root
    }

    private fun getCartData() {
        CoroutineScope(Dispatchers.IO).launch {
            cartViewModel.getCartList()
        }


        cartViewModel.cartList.observe(requireActivity()){
            when(it){
                is ResponseData.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                is ResponseData.Successful -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.size!! >0)
                    {
                        cartList = it.data
                        setupRecycler()

                    } else {
                        binding.cartRecycler.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE

                    }

                }
                is ResponseData.Error -> {
                    binding.notFound.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.cartRecycler.visibility = View.GONE

                }
            }
        }
    }

    private fun setupViewModel() {
        cartViewModel =ViewModelProvider(this,CartViewModelFactory(cartProductDao))[CartViewModel::class.java]
    }

    private fun setupRecycler() {
        binding.cartRecycler.layoutManager = LinearLayoutManager(requireContext(),VERTICAL,false)
        adapter = CartAdapter(requireContext(),cartList,this)
        binding.cartRecycler.adapter = adapter

        updateSubTotal()


    }

    fun updateSubTotal(){
        Handler(Looper.getMainLooper()).postDelayed({
            binding.totalPrice.setText("\u20B9 "+adapter.getSubTotal())
        }, 1000)
    }

    override fun deleteItem(id: Int) {

        cartViewModel.deleteCartItem(id)
        updateSubTotal()
    }

    override fun onClick(id: Int) {
        val intent = Intent(requireContext(),ProductDetailsActivity::class.java)
        intent.putExtra("productId",id)
        requireContext().startActivity(intent)
    }


}