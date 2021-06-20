package com.example.triviaquizapp.ui.splash

import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentSplashBinding
import com.example.triviaquizapp.repository.EmptyRepository

class SplashFragment : BaseFragment<FragmentSplashBinding, EmptyRepository>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): EmptyRepository {
        return EmptyRepository()
    }

    override fun onPostInit() {
        goToNextScreen()
    }

    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun goToNextScreen() {
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_quizModeFragment)

        }, 3000)

    }
}