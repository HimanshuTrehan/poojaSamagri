package com.slyked.poojasamagri.category.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.category.model.Category
import com.slyked.admin.product.model.Product
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.products.adapter.ProductAdapter
import com.slyked.poojasamagri.products.ui.ViewAllProducts
import com.slyked.poojasamagri.utils.Constants

class CategoryAdapter(context: Context,private val categoryItems: List<Category?>): PagingDataAdapter<Category,CategoryAdapter.ViewHolder>(COMPARATOR)   {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.home_category_view,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        holder.categoryName.setText(data?.name)
        try {

            val imageUrl = Constants.IMAGE_BASE_URL+data?.image
            Glide.with(ctx).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {

                override fun onLoadCleared(placeholder: Drawable?) {

                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    holder.categoryImage.setImageBitmap(resource)
                }
            })

        }catch (e:java.lang.Exception)
        {
            println(e)
        }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> goDetailsPage(it1.id) }
        }



    }


     class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val categoryName = itemView.findViewById<TextView>(R.id.category_txt)
        val categoryImage = itemView.findViewById<ImageView>(R.id.category_img)

    }

    private fun goDetailsPage(id:Int) {
        val intent = Intent(ctx , ViewAllProducts::class.java)
        intent.putExtra("categoryId", id)
        ctx.startActivity(intent)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }


}