package com.example.a2by3_android.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a2by3_android.R
import com.example.a2by3_android.model.SearchHistory
import kotlinx.android.synthetic.main.item_search_suggesstion.view.*

class SearchAdapter(private val searchHistoryArrayList: MutableList<SearchHistory>, private val click:ClickListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_search_suggesstion,parent,false)
        // return the view holder
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mSearchSuggesstionTextView.text = searchHistoryArrayList[position].name
        holder.itemView.setOnClickListener {
            click.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return searchHistoryArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mSearchSuggesstionTextView: TextView = itemView.searchTextView
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