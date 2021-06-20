package com.example.triviaquizapp.ui.won

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentGameWonBinding
import com.example.triviaquizapp.repository.EmptyRepository
import kotlinx.android.synthetic.main.fragment_game_won.*

class WonFragment : BaseFragment<FragmentGameWonBinding , EmptyRepository>() {
    val args: WonFragmentArgs by navArgs()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameWonBinding {
        return FragmentGameWonBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): EmptyRepository {
        return EmptyRepository()
    }

    override fun onPostInit() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                findNavController().navigate(R.id.action_wonFragment_to_quizMode)
            }
        })
        txtScorePoints.text = args.score
        goToHomeScreen()
    }

    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun goToHomeScreen() {
        playagainbutton.setOnClickListener {
                findNavController().navigate(R.id.action_wonFragment_to_categoryListFragment)
        }
    }
}