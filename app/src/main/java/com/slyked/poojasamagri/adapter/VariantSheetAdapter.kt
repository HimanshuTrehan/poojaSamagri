package com.slyked.poojasamagri.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.Product
import com.slyked.admin.product.model.ProductsVariant
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.utils.Constants

class VariantSheetAdapter(context: Context,private val product:Product ,private val productVariant:List<ProductsVariant>): RecyclerView.Adapter<VariantSheetAdapter.ViewHolder>()  {

    val ctx: Context = context
    val productCountMap = mutableMapOf<Int , Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.variant_sheet_item,parent,false)
        return ViewHolder(productView)
    }

    fun getVariantsData(): MutableMap<Int, Int> {
        return productCountMap
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var count = 1

        val data = productVariant[position]

        holder.product_name.text = product.name
        holder.quantity_txt.text = "Quantity: " + data.qty
        holder.product_price.text = "\u20B9 "+data.price
        holder.selected_quantity.text = count.toString()
        try {

            val imageUrl = Constants.IMAGE_BASE_URL+product.image
            Glide.with(ctx).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {

                override fun onLoadCleared(placeholder: Drawable?) {

                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    holder.product_image.setImageBitmap(resource)
                }
            })

        }catch (e:java.lang.Exception)
        {
            println(e)
        }


        holder.add_layout.setOnClickListener {
            holder.add_layout.visibility = GONE
            holder.product_operation_layout.visibility  = VISIBLE

        }
        holder.add_quantity.setOnClickListener {
            ++count
            holder.selected_quantity.text = count.toString()
            if (productCountMap.containsKey(data.id)){
                productCountMap.set(data.id!!,count)
            }else{
                productCountMap.put(data.id!!,count)
            }

        }
        holder.minus_quantity.setOnClickListener {
           --count
            holder.selected_quantity.text = count.toString()
            if (productCountMap.containsKey(data.id)){
                productCountMap.set(data.id!!,count)
            }else{
                productCountMap.put(data.id!!,count)
            }
        }
//        holder.deleteProduct.setOnClickListener {
//            listener.deleteItem(data.id)
//        }

    }

    override fun getItemCount(): Int {
        return productVariant.size
    }

    public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val product_name = itemView.findViewById<TextView>(R.id.product_name)
        val product_image = itemView.findViewById<ImageView>(R.id.product_image)
        val add_layout = itemView.findViewById<TextView>(R.id.add_layout)
        val add_quantity = itemView.findViewById<ImageView>(R.id.add_quantity)
        val minus_quantity = itemView.findViewById<ImageView>(R.id.minus_quantity)
        val quantity = itemView.findViewById<TextView>(R.id.quantity)

        // val deleteProduct = itemView.findViewById<ImageView>(R.id.deleteItem)
        val quantity_txt = itemView.findViewById<TextView>(R.id.quantity_txt)
        val product_price = itemView.findViewById<TextView>(R.id.product_price)
        val selected_quantity = itemView.findViewById<TextView>(R.id.quantity)
        val product_operation_layout = itemView.findViewById<ConstraintLayout>(R.id.product_operation_layout)



    }



}