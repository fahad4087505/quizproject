package com.example.a2by3_android.ui.home

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.databinding.FragmentHomeBinding
import com.example.a2by3_android.repository.EmptyRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding , BaseRepository>() {

  private val viewModel: HomeViewModel by viewModels()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): BaseRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {

  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }


}