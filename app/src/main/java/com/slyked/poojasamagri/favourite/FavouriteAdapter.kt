package com.slyked.poojasamagri.favourite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.utils.Constants

class FavouriteAdapter(context: Context,private val favouriteList:List<FavouriteProduct>,private val listener: ProductListener): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = favouriteList.get(position)
        holder.productName.text = data?.name
        holder.productQuanity.text = "Quantity: ${data?.ProductsVariants?.get(0)?.qty}"

        // println("MyImage "+ imageurl)
        try {

            val imageUrl = Constants.IMAGE_BASE_URL+data?.image
            Glide.with(ctx).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {

                override fun onLoadCleared(placeholder: Drawable?) {

                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    holder.productImage.setImageBitmap(resource)
                }
            })

        }catch (e:java.lang.Exception)
        {
            println(e)
        }
        holder.productFavourite.setImageResource(R.drawable.ic_favorite_bold)


        holder.itemView.setOnClickListener {
            listener.onClick(data.id)
        }

        holder.productFavourite.setOnClickListener {
            listener.removeFromFavourite(data.id)
        }

    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val add_layout = itemView.findViewById<LinearLayout>(R.id.add_layout)
        val minus = itemView.findViewById<ImageView>(R.id.minus_quantity)
        val quantity_layout = itemView.findViewById<LinearLayout>(R.id.quantity_layout)
        val productName = itemView.findViewById<TextView>(R.id.productName_singleProduct)
        val productQuanity = itemView.findViewById<TextView>(R.id.productQuantity_singleProduct)
        val productPrice = itemView.findViewById<TextView>(R.id.productPrice_singleProduct)
        val productImage = itemView.findViewById<ImageView>(R.id.productImage_singleProduct)
        val productFavourite = itemView.findViewById<ImageView>(R.id.productAddToFav_singleProduct)

    }

    interface ProductListener{
        fun removeFromFavourite(id:Int)
        fun onClick(id:Int)
    }
}