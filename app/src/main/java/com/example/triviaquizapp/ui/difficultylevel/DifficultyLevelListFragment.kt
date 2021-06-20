package com.example.triviaquizapp.ui.difficultylevel
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentDifficultyListBinding
import com.example.triviaquizapp.datasource.CategoriesListDataSource
import com.example.triviaquizapp.repository.DifficultyLevelListRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_difficulty_list.*
import kotlinx.android.synthetic.main.fragment_difficulty_list.backImage
import javax.inject.Inject

@AndroidEntryPoint
class DifficultyLevelListFragment :
    BaseFragment<FragmentDifficultyListBinding, DifficultyLevelListRepository>(){
    @Inject
    lateinit var dataSource: CategoriesListDataSource
    lateinit var viewModel: DifficultyListViewModel
    val args: DifficultyLevelListFragmentArgs by navArgs()
    var categoryId="";
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDifficultyListBinding {
        return FragmentDifficultyListBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): DifficultyLevelListRepository {
        return DifficultyLevelListRepository(dataSource)
    }

    override fun onPostInit() {
        initView()
        viewModel = ViewModelProvider(this, factory).get(DifficultyListViewModel::class.java)
        viewModel.fetchSellingtProductsList()
        setUpObserver()
    }
    private fun initView() {
        categoryId=args.categoryId
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                findNavController().navigate(R.id.action_difficultyLevelListFragment_to_Categories)
            }
        })
        backImage.setOnClickListener {
            findNavController().navigate(R.id.action_difficultyLevelListFragment_to_Categories)
        }
        easyTypeButton.setOnClickListener {
            val action = DifficultyLevelListFragmentDirections.actionDifficultyLevelListFragmentToQuestionTypeFragment(categoryId,"easy")
            findNavController().navigate(action)
        }
        mediumTypeButton.setOnClickListener {
            val action = DifficultyLevelListFragmentDirections.actionDifficultyLevelListFragmentToQuestionTypeFragment(categoryId,"medium")
            findNavController().navigate(action)
        }
        hardButton.setOnClickListener {
            val action = DifficultyLevelListFragmentDirections.actionDifficultyLevelListFragmentToQuestionTypeFragment(categoryId,"hard")
            findNavController().navigate(action)
        }
    }
    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun setUpObserver() {
//        viewModel.sellingProductsList.observe(this, {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    progressBar.hide()
//                    it.data?.data?.let { it1 ->
//                        categoryArrayList.addAll(it1)
//                        categoriesRecyclerView.adapter?.notifyDataSetChanged()
//                    }
//                }
//                Resource.Status.ERROR -> {
//                    progressBar.hide()
//                    Toast.makeText(
//                        requireContext(),
//                        it.responseError?.errorMessage,
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//                Resource.Status.LOADING -> {
//
//                }
//            }
//        }
//        )
    }
}