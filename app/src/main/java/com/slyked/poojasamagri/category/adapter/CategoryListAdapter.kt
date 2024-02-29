package com.slyked.poojasamagri.category.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Layout
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
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.utils.Constants

class CategoryListAdapter(
    private val context: Context, private val listener: ItemOperationListener
) : PagingDataAdapter<Category, CategoryListAdapter.ViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val categoryView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_view, parent, false)
        return ViewHolder(categoryView);

    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        holder.nameTxt.text = data?.name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.nameTxt.breakStrategy = Layout.BREAK_STRATEGY_BALANCED
        };

        try {

            val imageUrl = Constants.IMAGE_BASE_URL + data?.image
            Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap?>() {


                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap, transition: Transition<in Bitmap?>?
                ) {
                    holder.categoryImage.setImageBitmap(resource)
                }
            })

        } catch (e: java.lang.Exception) {
            println(e)
        }

        holder.itemView.setOnClickListener {
            if (data != null) {
                listener.onClicked(data.id)
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxt = itemView.findViewById<TextView>(R.id.name_txt)
        val categoryImage = itemView.findViewById<ImageView>(R.id.category_image)
        val expandImageview = itemView.findViewById<ImageView>(R.id.expand_icon)

    }

    interface ItemOperationListener {
        fun onClicked(id: Int)
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