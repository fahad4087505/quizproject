package com.example.a2by3_android.ui.dashboard

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.example.a2by3_android.SellingListAdapter
import com.example.a2by3_android.adapter.BidsAdapter
import com.example.a2by3_android.adapter.CategoriesAdapter
import com.example.a2by3_android.adapter.SearchAdapter
import com.example.a2by3_android.adapter.WatchListAdapter
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.data.sellingproductlistapi.Category
import com.example.a2by3_android.databinding.FragmentDashboardBinding
import com.example.a2by3_android.model.Bids
import com.example.a2by3_android.model.Categories
import com.example.a2by3_android.model.SearchHistory
import com.example.a2by3_android.model.WatchList
import com.example.a2by3_android.repository.EmptyRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding , BaseRepository>(),
  SearchAdapter.ClickListener, CategoriesAdapter.ClickListener, WatchListAdapter.ClickListener,
  BidsAdapter.ClickListener {

  private val viewModel: DashboardViewModel by viewModels()
  private var searchArrayList: ArrayList<SearchHistory> = arrayListOf()
  private var categoriesArrayList: ArrayList<Categories> = arrayListOf()
  private var watchListArrayList: ArrayList<WatchList> = arrayListOf()
  private var bidsArrayList: ArrayList<Bids> = arrayListOf()
  private lateinit var categoriesAdapter: CategoriesAdapter
  private lateinit var watchListAdapter: WatchListAdapter
  private lateinit var bidsAdapter: BidsAdapter

//  private var searchArrayList: ArrayList<String> = arrayListOf()

  override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDashboardBinding {
    return FragmentDashboardBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): BaseRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    setSuggestionAdapter()
  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun setSuggestionAdapter(){
    searchArrayList!!.add(SearchHistory("Search"))
    searchArrayList.add(SearchHistory("Search"))
    searchArrayList.add(SearchHistory("Search"))
    searchArrayList.add(SearchHistory("Search"))
    searchArrayList.add(SearchHistory("Search"))
    searchArrayList.add(SearchHistory("Search"))
    setSearchSuggesstionAdapter()
    categoriesArrayList!!.add(Categories("Search"))
    categoriesArrayList.add(Categories("Search"))
    categoriesArrayList.add(Categories("Search"))
    categoriesArrayList.add(Categories("Search"))
    categoriesArrayList.add(Categories("Search"))
    categoriesArrayList.add(Categories("Search"))
    setCategoriesAdapter()
    watchListArrayList!!.add(WatchList("Search"))
    watchListArrayList.add(WatchList("Search"))
    watchListArrayList.add(WatchList("Search"))
    watchListArrayList.add(WatchList("Search"))
    watchListArrayList.add(WatchList("Search"))
    watchListArrayList.add(WatchList("Search"))
    setWatchListAdapter()
    bidsArrayList!!.add(Bids("Search"))
    bidsArrayList.add(Bids("Search"))
    bidsArrayList.add(Bids("Search"))
    bidsArrayList.add(Bids("Search"))
    bidsArrayList.add(Bids("Search"))
    bidsArrayList.add(Bids("Search"))
    setBidsAdapter()
  }

  private fun setSearchSuggesstionAdapter(){
    val staggeredGridLayoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
    suggesstionRecyclerView.setLayoutManager(staggeredGridLayoutManager)
    val adapter = SearchAdapter(searchArrayList, this)
    suggesstionRecyclerView.setAdapter(adapter)
  }
  private fun setCategoriesAdapter(){
    categoriesAdapter = CategoriesAdapter(categoriesArrayList,this)
    val mLayoutManager = LinearLayoutManager(requireContext())
    mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    categoriesRecyclerView.layoutManager = mLayoutManager
    categoriesRecyclerView.itemAnimator = DefaultItemAnimator()
    categoriesRecyclerView.adapter = categoriesAdapter
  }
  private fun setWatchListAdapter(){
    watchListAdapter = WatchListAdapter(watchListArrayList,this)
    val mLayoutManager = LinearLayoutManager(requireContext())
    mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    watchListRecyclerView.layoutManager = mLayoutManager
    watchListRecyclerView.itemAnimator = DefaultItemAnimator()
    watchListRecyclerView.adapter = watchListAdapter
  }

  private fun setBidsAdapter(){
    bidsAdapter = BidsAdapter(bidsArrayList,this)
    val mLayoutManager = LinearLayoutManager(requireContext())
    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
    currentBidsRecyclerView.layoutManager = mLayoutManager
    currentBidsRecyclerView.itemAnimator = DefaultItemAnimator()
    currentBidsRecyclerView.adapter = bidsAdapter
  }

  override fun onItemClick(position: Int) {
  }

  override fun onItemLongClick(position: Int, v: View?) {
  }
}