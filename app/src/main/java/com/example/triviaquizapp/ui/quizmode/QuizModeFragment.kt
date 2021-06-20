package com.example.triviaquizapp.ui.quizmode

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentQuizModeBinding
import com.example.triviaquizapp.helper.SharedPrefrencesHelper
import com.example.triviaquizapp.helper.SharedPrefrencesHelper.APP_MODE
import com.example.triviaquizapp.repository.EmptyRepository
import com.example.triviaquizapp.ui.questiontype.QuestionTypeFragmentDirections
import kotlinx.android.synthetic.main.fragment_difficulty_list.*
import kotlinx.android.synthetic.main.fragment_quiz_mode.*

class QuizModeFragment : BaseFragment<FragmentQuizModeBinding, EmptyRepository>() {
    var categoryId = "";

    override fun getFragmentBinding(inflater: LayoutInflater,
        container: ViewGroup?): FragmentQuizModeBinding {
        return FragmentQuizModeBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): EmptyRepository {
        return EmptyRepository()
    }

    override fun onPostInit() {
        initView()
    }

    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun initView() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        })
        normalTypeButton.setOnClickListener {
            SharedPrefrencesHelper.write(APP_MODE, "normal")
            findNavController().navigate(R.id.action_quizModeFragment_to_sellingProductListFragment)
        }
        quickModeButton.setOnClickListener {
            SharedPrefrencesHelper.write(APP_MODE, "quick")
            val action = QuizModeFragmentDirections.actionQuizModeFragmentToQuestionFragment("","","any")
            findNavController().navigate(action)

        }
    }
}