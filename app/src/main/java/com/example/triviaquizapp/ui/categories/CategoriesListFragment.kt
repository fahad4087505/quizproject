package com.example.triviaquizapp.ui.categories
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaquizapp.R
import com.example.triviaquizapp.adapter.CategoriesListAdapter
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentCategoriesListBinding
import com.example.triviaquizapp.datasource.CategoriesListDataSource
import com.example.triviaquizapp.model.Categories
import com.example.triviaquizapp.repository.CategoriesListRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_categories_list.backImage
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesListFragment : BaseFragment<FragmentCategoriesListBinding, CategoriesListRepository>(),CategoriesListAdapter.ClickListener {
    @Inject
    lateinit var dataSource: CategoriesListDataSource
    lateinit var viewModel: CategoriesListViewModel
    val categoriesArrayList = ArrayList<Categories>()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCategoriesListBinding {
        return FragmentCategoriesListBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): CategoriesListRepository {
        return CategoriesListRepository(dataSource)
    }

    override fun onPostInit() {
//        activity!!.to!!.setHomeButtonEnabled(true)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                findNavController().navigate(R.id.action_categoriesListFragment_to_quizMode)
            }
        })
        backImage.setOnClickListener {
            findNavController().navigate(R.id.action_categoriesListFragment_to_quizMode)
        }
        viewModel = ViewModelProvider(this, factory).get(CategoriesListViewModel::class.java)
        categoriesArrayList.addAll(viewModel.allCategories())
        setAdapter()
    }


    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }
    private fun setAdapter() {
        // initialize grid layout manager
        GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false).apply {
            // specify the layout manager for recycler view
            categoriesRecyclerView.layoutManager = this
        }
        // finally, data bind the recycler view with adapter
        categoriesRecyclerView.adapter = CategoriesListAdapter(categoriesArrayList, this)
    }

    override fun onItemClick(position: Int) {
        val action = CategoriesListFragmentDirections.actionCaregoriesFragmentToDifficultyLevelListFragment(categoriesArrayList[position].categoryId)
        findNavController().navigate(action)
    }

}