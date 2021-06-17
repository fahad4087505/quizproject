package com.example.a2by3_android.ui.includedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.data.sellingproductlistapi.Category
import com.example.a2by3_android.databinding.FragmentIncludeDetailsBinding
import com.example.a2by3_android.datasource.IncludeDetailsDataSource
import com.example.a2by3_android.extensions.hide
import com.example.a2by3_android.network.Resource
import com.example.a2by3_android.repository.IncludeDetailsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_include_details.*
import kotlinx.android.synthetic.main.fragment_selling_product_list.*
import kotlinx.android.synthetic.main.fragment_selling_product_list.progressBar
import javax.inject.Inject


@AndroidEntryPoint
class IncludeDetailsFragment : BaseFragment<FragmentIncludeDetailsBinding, IncludeDetailsRepository>() {

    @Inject
    lateinit var dataSource: IncludeDetailsDataSource
    lateinit var viewModel: IncludeDetailViewModel
    var categoryArrayList = arrayListOf<Category>()
    private var selectedYear=""
    private var gradeTitle=""
    private var playerName=""
    private var parallelTitle=""
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): FragmentIncludeDetailsBinding {
        return FragmentIncludeDetailsBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): IncludeDetailsRepository {
        return IncludeDetailsRepository(dataSource)
    }

    override fun onPostInit() {
        viewModel = ViewModelProvider(this, factory).get(IncludeDetailViewModel::class.java)
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (activeTimeTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        setYearAdapter()
        setBrandAdapter()
        setParallelAdapter()
        setGradeAdapter()
        setProductAdapter()
        setPlayerAdapter()
        etShippingService.setOnClickListener {
            val popUpClass = PopUpClass()
            popUpClass.showPopupWindow(it)
        }
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_includeDetailsFragment_to_uploadPhotosFragment)
        }
    }

    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun setUpObserver() {
        viewModel.sellingProductsList.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.hide()
                    categoryArrayList.addAll(it.data?.data!!)
                    categoriesRecyclerView.adapter?.notifyDataSetChanged()
                }
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toast.makeText(requireContext(), it.responseError?.errorMessage, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun setYearAdapter() {
        val items = listOf("2019", "2018", "2017", "2016")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (yearTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        yearTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            selectedYear = parent.selectedItem.toString();
        })
//            Toast.makeText(ApplicationProvider.getApplicationContext<Context>(), "Selected Item: " + parent.selectedItem, Toast.LENGTH_SHORT).show() })
    }

    private fun setBrandAdapter() {
        val items = listOf("Panini", "Panini1", "Panini2", "Panini3")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (brandTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        brandTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            selectedYear = parent.selectedItem.toString();
        })
    }

    private fun setProductAdapter() {
        val items = listOf("NBA", "Product", "BaseBall")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (productTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        productTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            selectedYear = parent.selectedItem.toString();
        })
    }

    private fun setParallelAdapter() {
        val items = listOf("Lorem Ipsum", "Lorem Ipsum1", "Lorem Ipsum2")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (parallelTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        parallelTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            parallelTitle = parent.selectedItem.toString();
        })

    }
    private fun setPlayerAdapter() {
        val items = listOf("Lorem Ipsum", "Lorem Ipsum1", "Lorem Ipsum2")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (playerTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        playerTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            playerName = parent.selectedItem.toString();
        })
    }

    private fun setGradeAdapter() {
        val items = listOf("Lorem Ipsum", "Lorem Ipsum1", "Lorem Ipsum2")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (gradedTextView as? AutoCompleteTextView)?.setAdapter(adapter)
        gradedTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            gradeTitle = parent.selectedItem.toString();
        })
    }
}