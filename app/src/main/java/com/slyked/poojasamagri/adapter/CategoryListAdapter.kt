package com.slyked.poojasamagri.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.model.SubCategoryModel
import com.slyked.poojasamagri.ui.ViewAllProducts
import com.slyked.poojasamagri.utils.Constants
import com.slyked.poojasamagri.utils.Constants.Companion.CATEGORY_VIEW
import com.slyked.poojasamagri.utils.Constants.Companion.SUB_CATEGORY_VIEW

class CategoryListAdapter(private val context: Context, private val subCategoryList: ArrayList<SubCategoryModel>): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {



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
    override fun getItemViewType(position: Int): Int {
        return if (subCategoryList[position].subcategoryList.isEmpty()) {
            CATEGORY_VIEW
        } else {
            SUB_CATEGORY_VIEW
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ViewAllProducts::class.java)
            intent.flags =  FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {

    }



}