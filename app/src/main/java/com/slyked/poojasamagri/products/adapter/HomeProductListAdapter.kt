package com.slyked.poojasamagri.products.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.poojasamagri.PoojaApplication

import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import com.slyked.poojasamagri.products.ui.ProductDetailsActivity
import com.slyked.poojasamagri.utils.Constants
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class HomeProductListAdapter @Inject constructor(context: Context,private val favouriteProductDao: FavouriteProductDao,private val listener:ProductListener):  PagingDataAdapter<Product, HomeProductListAdapter.ViewHolder>(COMPARATOR) {

    private val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val data = getItem(position)
        holder.productName.text = data?.name
         holder.productQuanity.text = "Quantity: 1"
        val imageurl = Constants.IMAGE_BASE_URL+data?.image
        println("MyImage "+ imageurl)
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
      //  holder.quantity_layout.visibility = GONE
       // holder.add_layout.visibility = GONE
        holder.add_layout.setOnClickListener {
            listener.openVariants(data?.id!!)
        }
        if (data?.id !=null) {
            CoroutineScope(Dispatchers.IO).launch {
                if (favouriteProductDao.isItemExist(data.id.toString())) {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.productFavourite.setImageResource(R.drawable.ic_favorite_bold)
                    }
                }else
                {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.productFavourite.setImageResource(R.drawable.ic_favorite)
                    }
                }
            }

        }

//        holder.add_layout.setOnClickListener {
//            holder.add_layout.visibility = GONE
//            holder.quantity_layout.visibility = VISIBLE
//        }
//        holder.minus.setOnClickListener {
//            holder.add_layout.visibility = VISIBLE
//            holder.quantity_layout.visibility = GONE
//        }
        holder.itemView.setOnClickListener {
            if (data != null) {
                listener.onClick(data.id)
            }
        }
        holder.productFavourite.setOnClickListener {
            if (data != null) {
                listener.addToFavourite(data.id)
            }
        }

    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

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
        fun addToFavourite(id:Int)
        fun onClick(id:Int)
        fun openVariants(id:Int)

    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

}