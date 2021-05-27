package com.example.a2by3_android.ui.dashboard

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.databinding.FragmentDashboardBinding
import com.example.a2by3_android.repository.EmptyRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding , BaseRepository>() {

  private val viewModel: DashboardViewModel by viewModels()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentDashboardBinding {
    return FragmentDashboardBinding.inflate(inflater, container, false)
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