package com.slyked.poojasamagri.cart

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.product.model.CartProduct
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.utils.Constants

class CartAdapter(context: Context,private val cartList:List<CartProduct>,private val listener:CartListener): RecyclerView.Adapter<CartAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.cart_items,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = cartList.get(position)

        holder.product_name.text = data.name
        holder.quantity_txt.text = "Quantity: " + data.variant?.qty
        holder.product_price.text = "\u20B9 "+data.variant?.price
        holder.selected_quantity.text = "x "+data.quantity.toString()
        try {

            val imageUrl = Constants.IMAGE_BASE_URL+data?.image
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

        holder.itemView.setOnClickListener {
            listener.onClick(data.id)
        }
        holder.deleteProduct.setOnClickListener {
            listener.deleteItem(data.id)
        }

    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val product_name = itemView.findViewById<TextView>(R.id.product_name)
        val product_image = itemView.findViewById<ImageView>(R.id.product_image)
        val deleteProduct = itemView.findViewById<ImageView>(R.id.deleteItem)
        val quantity_txt = itemView.findViewById<TextView>(R.id.quantity_txt)
        val product_price = itemView.findViewById<TextView>(R.id.product_price)
        val selected_quantity = itemView.findViewById<TextView>(R.id.quantity)



    }

    interface CartListener{
        fun deleteItem(id:Int)
        fun onClick(id:Int)
    }


}