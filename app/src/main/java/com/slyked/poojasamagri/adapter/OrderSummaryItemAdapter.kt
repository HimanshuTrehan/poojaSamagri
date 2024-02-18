package com.slyked.poojasamagri.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slyked.poojasamagri.R
import com.slyked.poojasamagri.products.ui.ViewAllProducts

class OrderSummaryItemAdapter(context: Context): RecyclerView.Adapter<OrderSummaryItemAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryItemAdapter.ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.order_summary_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: OrderSummaryItemAdapter.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            goDetailsPage()
        }

    }

    override fun getItemCount(): Int {
        return 4
    }

     class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



    }

    private fun goDetailsPage() {
        val intent = Intent(ctx , ViewAllProducts::class.java)
        intent.putExtra("ProductFrom", "New")
        ctx.startActivity(intent)
    }
}