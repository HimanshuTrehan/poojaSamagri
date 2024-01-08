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


class ProductAdapter( context: Context,): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.single_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {

        holder.minus.visibility = GONE
        holder.quantity.visibility = GONE
        holder.add.setOnClickListener {
            holder.minus.visibility = VISIBLE
            holder.quantity.visibility = VISIBLE
        }
        holder.minus.setOnClickListener {
            holder.minus.visibility = GONE
            holder.quantity.visibility = GONE
        }
        holder.itemView.setOnClickListener {
            goDetailsPage()
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val add = itemView.findViewById<ImageView>(R.id.add_quantity)
        val minus = itemView.findViewById<ImageView>(R.id.minus_quantity)
        val quantity = itemView.findViewById<TextView>(R.id.quantity)


    }

    private fun goDetailsPage() {
        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductFrom", "New")
        ctx.startActivity(intent)
    }

}
