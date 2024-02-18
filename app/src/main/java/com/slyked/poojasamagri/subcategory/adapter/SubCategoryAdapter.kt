package com.slyked.poojasamagri.subcategory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slyked.admin.subcategory.model.SubCategory
import com.slyked.poojasamagri.R


class SubCategoryAdapter(context: Context, subCategoryList: List<SubCategory>, private val listener: ItemOperationListener): RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>()  {

    val ctx: Context = context
    val dataList : List<SubCategory> = subCategoryList
    private val PICK_IMAGE_REQUEST = 1 // Request code for the image picker

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.add_sub_category_list,parent,false)

        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList.get(position)
        holder.nameTxt.text = data.name

        holder.itemView.setOnClickListener {
          listener.onClicked(data.id)

        }

    }



    override fun getItemCount(): Int {
        return dataList.size
    }

     class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameTxt = itemView.findViewById<TextView>(R.id.name_txt)



    }

    interface ItemOperationListener{
        fun onClicked(id:Int)
    }







}