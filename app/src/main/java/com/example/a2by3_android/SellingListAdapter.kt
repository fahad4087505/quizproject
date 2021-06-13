package com.example.a2by3_android
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_categories.view.*

class SellingListAdapter(private val categories: MutableList<String>,private val click:ClickListener) : RecyclerView.Adapter<SellingListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_categories,parent,false)
        // return the view holder
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current animal
        holder.mCategoryTextView.text = categories[position]
        holder.relativeLayout.setOnClickListener {
            click.onItemClick(position)
        }
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return categories.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mCategoryTextView: TextView = itemView.categoriesTextView
        val relativeLayout: RelativeLayout = itemView.relativeLayout
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