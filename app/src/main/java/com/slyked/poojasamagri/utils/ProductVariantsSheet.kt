package com.slyked.poojasamagri.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.slyked.admin.product.model.Product
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.adapter.VariantSheetAdapter

class ProductVariantsSheet(context: Context,private val productData:Product,private val listeners: BottomSheetListeners) : BottomSheetDialog(context) {

    lateinit var recycler:RecyclerView
    lateinit var adapter: VariantSheetAdapter

    lateinit var addBtn:Button

    @SuppressLint("MissingInflatedId")
    fun showDialog(){

        val view = layoutInflater.inflate(R.layout.product_variant_sheet, null)

        recycler = view.findViewById(R.id.variantRecycler)
        adapter = VariantSheetAdapter(context,productData,productData.ProductsVariants!!)

        recycler.layoutManager = LinearLayoutManager(context,VERTICAL,false)
        recycler.adapter = adapter

        addBtn = view.findViewById(R.id.add_btn)

        addBtn.setOnClickListener {
            val data =  adapter.getVariantsData()
            listeners.addToCart(productData,data)
            this.dismiss()
        }



       // this.setCancelable(false)

        // on below line we are setting
        // content view to our view.
        this.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        this.show()



    }

    interface BottomSheetListeners{
        fun addToCart(productData: Product, data: MutableMap<Int, Int>)

    }



}