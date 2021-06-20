package com.example.a2by3_android.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a2by3_android.R
import com.example.a2by3_android.model.Bids
import kotlinx.android.synthetic.main.item_bids.view.*

class BidsAdapter(private val bidsList: MutableList<Bids>, private val click:ClickListener) : RecyclerView.Adapter<BidsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bids,parent,false)
        // return the view holder
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mCategoriesNameTextView.text = bidsList[position].name
        holder.itemView.setOnClickListener {
            click.onItemClick(position)
        }
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return bidsList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mCategoriesNameTextView: TextView = itemView.productNameTextView
    }

    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    interface ClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int, v: View?)
    }
}