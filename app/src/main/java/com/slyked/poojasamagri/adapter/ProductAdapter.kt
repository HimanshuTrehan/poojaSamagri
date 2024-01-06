package com.slyked.poojasamagri.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.ui.ProductDetailsActivity


class ProductAdapter( context: Context,): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            goDetailsPage()
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

//        val productImage_singleProduct:ImageView = itemView.findViewById(R.id.productImage_singleProduct)
//        val productRating_singleProduct:RatingBar = itemView.findViewById(R.id.productRating_singleProduct)
//        val productBrandName_singleProduct:TextView = itemView.findViewById(R.id.productBrandName_singleProduct)
//        val discountTv_singleProduct:TextView = itemView.findViewById(R.id.discountTv_singleProduct)
//        val productName_singleProduct:TextView = itemView.findViewById(R.id.productName_singleProduct)
//        val productPrice_singleProduct:TextView = itemView.findViewById(R.id.productPrice_singleProduct)
//        val discount_singleProduct = itemView.findViewById<LinearLayout>(R.id.discount_singleProduct)


    }

    private fun goDetailsPage() {
        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductFrom", "New")
        ctx.startActivity(intent)
    }

}
