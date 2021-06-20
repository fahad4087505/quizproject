package com.example.triviaquizapp.ui.questions

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.triviaquizapp.R
import com.example.triviaquizapp.base.BaseFragment
import com.example.triviaquizapp.data.response.questionsmodel.Result
import com.example.triviaquizapp.databinding.FragmentQuestionsBinding
import com.example.triviaquizapp.datasource.QuestionsDataSource
import com.example.triviaquizapp.helper.SharedPrefrencesHelper
import com.example.triviaquizapp.helper.SharedPrefrencesHelper.APP_MODE
import com.example.triviaquizapp.network.Resource
import com.example.triviaquizapp.repository.QuestionsRepository
import dagger.hilt.android.AndroidEntryPoint
import info.hoang8f.widget.FButton
import kotlinx.android.synthetic.main.fragment_questions.*
import javax.inject.Inject

@AndroidEntryPoint
class QuestionsFragment : BaseFragment<FragmentQuestionsBinding, QuestionsRepository>() {
    @Inject
    lateinit var dataSource: QuestionsDataSource
    lateinit var viewModel: QuestionsViewModel
    var list: List<Result>? = null
    val questionsArrayList = ArrayList<Result>()
    var wrongQuestionCount=0
    var questionPosition = 0
    var timeValue = 5
    var coinValue = 0
    var countDownTimer: CountDownTimer? = null

    val args: QuestionsFragmentArgs by navArgs()
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentQuestionsBinding {
        return FragmentQuestionsBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): QuestionsRepository {
        return QuestionsRepository(dataSource)
    }

    private fun init() {
        buttonAOption.setOnClickListener {
            moveToNextQuestion(buttonAOption)
        }
        buttonBOption.setOnClickListener {
            moveToNextQuestion(buttonBOption)
        }
        buttonCOption.setOnClickListener {
            moveToNextQuestion(buttonCOption)
        }
        buttonDOption.setOnClickListener {
            moveToNextQuestion(buttonDOption)
        }
        txtNext.setOnClickListener {
            if (timeValue > 1) {
                if (buttonAOption.text.toString().equals(questionsArrayList[questionPosition].correct_answer)) {
                    buttonAOption.buttonColor = ContextCompat.getColor(requireContext(), R.color.lightGreen)
                    if(questionsArrayList[questionPosition].type.equals("easy")) {
                        coinValue += 1
                    }else if(questionsArrayList[questionPosition].type.equals("medium")){
                        coinValue += 2
                    }else {
                        coinValue += 3
                    }
                }
                if (questionPosition <= questionsArrayList.size - 2) {
                    questionPosition += 1
                    setQuestions(questionPosition)
                }
            }
        }
        if(SharedPrefrencesHelper.read(APP_MODE, "").equals("quick")){
            timeText.visibility=View.VISIBLE
            txtNext.visibility=View.GONE
        }
    }

    private fun timer() {
        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //here you can have your logic to set text to timeText
                if(timeValue!=null) {
                    timeText!!.text = timeValue.toString() + ""
                    timeValue-=1
                }
                //With each iteration decrement the time by 1 sec
                //This means the user is out of time so onFinished will called after this iteration
            }

            override fun onFinish() {
                if (questionPosition <= questionsArrayList.size - 2) {
                    questionPosition += 1
                    setQuestions(questionPosition)
                    if (questionPosition == questionsArrayList.size - 1) {
                        txtNext.text = "End"
                    }
                    countDownTimer!!.cancel()
                    countDownTimer = null
                    timer()
                    timeValue = 5
                } else {
                    val action = QuestionsFragmentDirections.actionQuestionsToWonFragment("","hard")
                    findNavController().navigate(action)
                }
            }
        }.start()
    }
    override fun onPostInit() {
        viewModel = ViewModelProvider(this, factory).get(QuestionsViewModel::class.java)

        if(args.questionType.equals("boolean")){
            buttonCOption.visibility=View.GONE
            buttonDOption.visibility=View.GONE
        }
        val hashMap = HashMap<String, String>()
        if(SharedPrefrencesHelper.read(APP_MODE, "").equals("quick")) {
            hashMap["amount"] = "50"
        }else{
            hashMap["category"] = args.categoryId
            hashMap["difficulty"] = args.difficultyId
            hashMap["type"] = args.questionType
            hashMap["amount"] = "10"
        }
        progressBar.show(requireContext())
        viewModel.fetchQuestionsList(hashMap)
        setUpObserver()
        init()
    }

    override fun onOptionsSelected(item: MenuItem) {
    }

    override fun onActivityCreation() {
    }

    private fun setUpObserver() {
        viewModel._questionsMutableList.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.dialog.dismiss()
                    it.data?.results?.let { it1 ->
                        if (it1.size > 0) {
                            questionsArrayList.addAll(it1)
                            resultText!!.text = questionsArrayList[questionPosition].question
                            setQuestions(questionPosition)
                            if (SharedPrefrencesHelper.read(APP_MODE, "").equals("quick")) {
                                timer()
                                timeText.visibility = View.VISIBLE
                                txtNext.visibility = View.GONE
                            }
                        } else {
                            triviaQuestion!!.visibility = View.GONE
                            resultText!!.text = "No Questions Found"
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    progressBar.dialog.dismiss()
                    Toast.makeText(requireContext(), it.responseError?.errorMessage, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                }
            }
        }
        )
    }

    private fun setQuestions(position: Int) {
        buttonAOption.buttonColor = ContextCompat.getColor(requireContext(), R.color.blue)
        buttonBOption.buttonColor = ContextCompat.getColor(requireContext(), R.color.blue)
        buttonCOption.buttonColor = ContextCompat.getColor(requireContext(), R.color.blue)
        buttonDOption.buttonColor = ContextCompat.getColor(requireContext(), R.color.blue)
        resultText!!.text = questionsArrayList[questionPosition].question
        if (questionsArrayList[position].type.equals("multiple")) {
            buttonAOption!!.text = questionsArrayList[questionPosition].correct_answer
            buttonBOption!!.text = questionsArrayList[questionPosition].incorrect_answers[0]
            buttonCOption!!.text = questionsArrayList[questionPosition].incorrect_answers[1]
            buttonDOption!!.text = questionsArrayList[questionPosition].incorrect_answers[2]
        }else{
            buttonAOption!!.text = questionsArrayList[questionPosition].correct_answer
            buttonBOption!!.text = questionsArrayList[questionPosition].incorrect_answers[0]
            buttonCOption!!.visibility= View.GONE
            buttonDOption!!.visibility= View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    private fun moveToNextQuestion(button: FButton) {
        if (questionPosition <= questionsArrayList.size - 2) {
            if (timeValue > 1) {
                if (button.text.toString().equals(questionsArrayList[questionPosition].correct_answer)) {
                    button.buttonColor = ContextCompat.getColor(requireContext(), R.color.lightGreen)
                    coinValue += 1
                    coinText.text = coinValue.toString()
                }else{
                    if(SharedPrefrencesHelper.read(APP_MODE, "").equals("quick")) {
                        wrongQuestionCount += 1
                        if (wrongQuestionCount > 3) {
                            wrongQuestionCount = 0
                            val action = QuestionsFragmentDirections.actionQuestionsToWonFragment(coinValue.toString(),"loss")
                            findNavController().navigate(action)
                        }
                    }
                }
                questionPosition += 1
                setQuestions(questionPosition)
                if(countDownTimer!=null) {
                    countDownTimer!!.cancel()
                    countDownTimer = null
                }
                timeValue=5
                timer()
            }
        } else {
            val action = QuestionsFragmentDirections.actionQuestionsToWonFragment(coinValue.toString(),"win")
            findNavController().navigate(action)
        }
    }
}