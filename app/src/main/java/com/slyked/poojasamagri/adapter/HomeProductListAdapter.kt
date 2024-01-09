package com.slyked.poojasamagri.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.ui.ProductDetailsActivity


class HomeProductListAdapter(context: Context): RecyclerView.Adapter<HomeProductListAdapter.ViewHolder>()  {

    private val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductListAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: HomeProductListAdapter.ViewHolder, position: Int) {

        holder.quantity_layout.visibility = GONE
        holder.add_layout.setOnClickListener {
            holder.add_layout.visibility = GONE
            holder.quantity_layout.visibility = VISIBLE
        }
        holder.minus.setOnClickListener {
            holder.add_layout.visibility = VISIBLE
            holder.quantity_layout.visibility = GONE
        }
        holder.itemView.setOnClickListener {
            goDetailsPage()
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val add_layout = itemView.findViewById<LinearLayout>(R.id.add_layout)
        val minus = itemView.findViewById<ImageView>(R.id.minus_quantity)
        val quantity_layout = itemView.findViewById<LinearLayout>(R.id.quantity_layout)


    }

    private fun goDetailsPage() {
        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductFrom", "New")
        ctx.startActivity(intent)
    }

}
