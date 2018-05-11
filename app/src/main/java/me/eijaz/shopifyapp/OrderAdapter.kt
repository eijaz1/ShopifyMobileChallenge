package me.eijaz.shopifyapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.order_list_item.view.*

class OrderAdapter(val orders: List<Order>) : RecyclerView.Adapter<CustomViewHolder>() {

    //Update UI with a max of 10 orders
    override fun getItemCount(): Int {
        return if (orders.size <= 10) {
            orders.size
        } else {
            10
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.order_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //Show id, first name and last name for each order
    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

        holder?.itemView?.tv_order_id?.text = "Order #: " + orders[position].id.toString()

        if (orders[position].customer?.first_name != null && orders[position].customer?.last_name != null) {
            holder?.itemView?.tv_order_year?.text = "Name: " + orders[position].customer?.first_name + " " + orders[position].customer?.last_name
        } else {
            holder?.itemView?.tv_order_year?.text = "Name: "
        }
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view)


