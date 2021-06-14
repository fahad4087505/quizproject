package com.example.a2by3_android.ui.sell

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2by3_android.R
import com.example.a2by3_android.SellingListAdapter
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSellingProductListBinding
import com.example.a2by3_android.datasource.SellingProductListDataSource
import com.example.a2by3_android.extensions.hide
import com.example.a2by3_android.network.Resource
import com.example.a2by3_android.repository.SellingProductListRepository
import com.example.a2by3_android.ui.includedetails.IncludeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_selling_product_list.*
import javax.inject.Inject

@AndroidEntryPoint
class SellingProductListFragment : BaseFragment<FragmentSellingProductListBinding , SellingProductListRepository>(),SellingListAdapter.ClickListener {

  @Inject
  lateinit var dataSource: SellingProductListDataSource
  lateinit var viewModel: SellingProductListViewModel
  private var categoryArrayList: ArrayList<String> = arrayListOf()


  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSellingProductListBinding {
    return FragmentSellingProductListBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): SellingProductListRepository {
    return SellingProductListRepository(dataSource)
  }

  override fun onPostInit() {
    viewModel = ViewModelProvider(this, factory).get(SellingProductListViewModel::class.java)
    viewModel.fetchSellingtProductsList()

    setAdapter()
    setUpObserver()
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun setUpObserver() {
    viewModel.sellingProductsList.observe(this) {
      when(it.status) {
        Resource.Status.SUCCESS -> {
          progressBar.hide()
          categoryArrayList.addAll(it.data?.data!!)
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          it.data?.data?.let { it1 -> categoryArrayList.addAll(it1) }
          categoriesRecyclerView.adapter?.notifyDataSetChanged()
          Toast.makeText(requireContext(), it.data?.status.toString(), Toast.LENGTH_SHORT).show()
        }
        Resource.Status.ERROR -> {
          progressBar.hide()
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoryArrayList.add("BaseBall")
          categoriesRecyclerView.adapter?.notifyDataSetChanged()
          Toast.makeText(requireContext(), it.responseError?.errorMessage, Toast.LENGTH_SHORT).show()
        }

        Resource.Status.LOADING -> {

        }
      }
    }
  }

  private fun setAdapter(){
    // initialize grid layout manager
    GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false).apply {
      // specify the layout manager for recycler view
    categoriesRecyclerView.layoutManager =this
    }
    // finally, data bind the recycler view with adapter
    categoriesRecyclerView.adapter = SellingListAdapter(categoryArrayList,this)
  }

  override fun onItemClick(position: Int) {
    findNavController().navigate(R.id.action_splashFragment_to_includeDetailsFragment)
  }

  override fun onItemLongClick(position: Int, v: View?) {
    TODO("Not yet implemented")
  }
}