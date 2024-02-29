package com.slyked.poojasamagri.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.slyked.admin.configuration.model.Banner
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.utils.Constants

class BannerAdapter(private val context: Context,private val bannerItems: List<Banner?>): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.ad_banner,parent,false)
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = bannerItems[position]

        try {

            val imageUrl = Constants.IMAGE_BASE_URL + data?.image
            Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {

                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    holder.image.setImageBitmap(resource)
                }
            })

        } catch (e: java.lang.Exception) {
            println(e)
        }
    }

    override fun getItemCount(): Int {
        return bannerItems.size
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.findViewById<ImageView>(R.id.bannerImage)
    }
}