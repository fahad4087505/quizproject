package com.example.triviaquizapp.base
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.triviaquizapp.helper.SharedPrefrencesHelper
import com.example.triviaquizapp.view.CustomProgressBar

/**
Created By Fahad on 18/06/21
 */
abstract class BaseFragment<B : ViewBinding, R : BaseRepository> : Fragment() {
    protected lateinit var binding: B
    protected lateinit var factory: ViewModelFactory
    var progressBar=CustomProgressBar()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater, container)
        factory = ViewModelFactory(getRepository())
        SharedPrefrencesHelper.init(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            onPostInit()
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): R

    abstract fun onPostInit()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onOptionsSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onActivityCreation()
    }

    abstract fun onOptionsSelected(item: MenuItem)

    abstract fun onActivityCreation()

    // define your base methods here like nav drawer/bottom nav etc
    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.show()
    }
}