package com.example.triviaquizapp.ui.questiontype
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.databinding.FragmentQuestionTypeBinding
import com.example.triviaquizapp.repository.EmptyRepository
import kotlinx.android.synthetic.main.fragment_question_type.*
import kotlinx.android.synthetic.main.fragment_question_type.backImage

class QuestionTypeFragment : BaseFragment<FragmentQuestionTypeBinding, EmptyRepository>() {
    val args: QuestionTypeFragmentArgs by navArgs()
    var categoryId="";
    var difficultyType="";

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentQuestionTypeBinding {
        return FragmentQuestionTypeBinding.inflate(inflater, container, false)
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
        categoryId=args.categoryId
        difficultyType=args.difficultyId
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                findNavController().navigate(R.id.action_difficultyLevelListFragment_to_Categories)
            }
        })
        backImage.setOnClickListener {
            findNavController().navigate(R.id.action_difficultyLevelListFragment_to_Categories)
        }
        multipleChoiceQuestionTypeButton.setOnClickListener {
            val action = QuestionTypeFragmentDirections.actionQuestionsTypeToQuestionsFragment(categoryId,difficultyType,"multiple")
            findNavController().navigate(action)
        }
        trueQuestionTypeButton.setOnClickListener {
            val action = QuestionTypeFragmentDirections.actionQuestionsTypeToQuestionsFragment(categoryId,difficultyType,"boolean")
            findNavController().navigate(action)
        }
    }
}