package com.slyked.poojasamagri.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.utils.Constants

class CategoryListAdapter(context: Context): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Constants.CATEGORY_VIEW -> {
                val categoryView = LayoutInflater.from(parent.context).inflate(R.layout.category_view,parent,false)
                ViewHolder(categoryView);
            }

            else -> {
                val subCategoryView = LayoutInflater.from(parent.context).inflate(R.layout.sub_category_view,parent,false)
                ViewHolder(subCategoryView);
            }
        }

    }
//    override fun getItemViewType(position: Int): Int {
//        return
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {

    }



}