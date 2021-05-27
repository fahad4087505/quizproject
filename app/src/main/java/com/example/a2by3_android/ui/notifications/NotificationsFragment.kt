package com.example.a2by3_android.ui.notifications

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.databinding.FragmentNotificationsBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding , BaseRepository>() {

  private val viewModel: DashboardViewModel by viewModels()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentNotificationsBinding {
    return FragmentNotificationsBinding.inflate(inflater, container, false)
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