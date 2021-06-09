package com.example.a2by3_android.ui.signup

import android.app.DatePickerDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignupDetailsBinding
import com.example.a2by3_android.enum.SignUpMethod
import com.example.a2by3_android.helper.SharedPrefrencesHelper
import com.example.a2by3_android.model.User
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_signup_details.btnCreateAccount
import kotlinx.android.synthetic.main.fragment_signup_details.etAddressLine1
import kotlinx.android.synthetic.main.fragment_signup_details.etAddressLine2
import kotlinx.android.synthetic.main.fragment_signup_details.etCity
import kotlinx.android.synthetic.main.fragment_signup_details.etCountry
import kotlinx.android.synthetic.main.fragment_signup_details.etDateOfBirth
import kotlinx.android.synthetic.main.fragment_signup_details.etEmail
import kotlinx.android.synthetic.main.fragment_signup_details.etEmailLayout
import kotlinx.android.synthetic.main.fragment_signup_details.etPassword
import kotlinx.android.synthetic.main.fragment_signup_details.etPasswordLayout
import kotlinx.android.synthetic.main.fragment_signup_details.etPostCode
import kotlinx.android.synthetic.main.fragment_signup_details.etRepeatPassword
import kotlinx.android.synthetic.main.fragment_signup_details.etRepeatPasswordLayout
import kotlinx.android.synthetic.main.fragment_signup_details.progressBar
import kotlinx.android.synthetic.main.fragment_signup_details.tvIncludeSomeDetails


class SignupDetailsFragment : BaseFragment<FragmentSignupDetailsBinding , EmptyRepository>() {

  private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSignupDetailsBinding {
    return FragmentSignupDetailsBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    btnCreateAccount.setOnClickListener {
      validateUserInputs()
    }

    etDateOfBirth.setOnClickListener {
      showDatePickerDialog()
    }

    tvIncludeSomeDetails.setOnClickListener {
      findNavController().navigateUp()
    }
  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun validateUserInputs() {
    var isAnyErrorOccurred = false
    etEmailLayout.error = null
    etPasswordLayout.error = null
    etRepeatPasswordLayout.error = null

    if (!TextUtils.isEmpty(etEmail.text.toString()) && !TextUtils.isEmpty(etPassword.text.toString())
      && !TextUtils.isEmpty(etRepeatPassword.text.toString()) && !TextUtils.isEmpty(etDateOfBirth.text.toString())
      && !TextUtils.isEmpty(etCountry.text.toString()) && !TextUtils.isEmpty(etCity.text.toString())
      && !TextUtils.isEmpty(etAddressLine1.text.toString()) && !TextUtils.isEmpty(etAddressLine2.text.toString())
      && !TextUtils.isEmpty(etPostCode.text.toString())) {

      if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
        isAnyErrorOccurred = true
        etEmailLayout.error = "Please Enter a Valid Email Address"
      }

      if (etPassword.text.toString().length < 8) {
        isAnyErrorOccurred = true
        etPasswordLayout.error = "Please Enter at least 8 digits Password"
      }

      if (etRepeatPassword.text.toString() != etPassword.text.toString()) {
        isAnyErrorOccurred = true
        etRepeatPasswordLayout.error = "Passwords don't match"
      }

      if (!isAnyErrorOccurred) {
        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              val user = mAuth.currentUser
              val userData = User()
              userData.userUid = user?.uid
              userData.userEmail = etEmail.text.toString()
              userData.userPassword = etPassword.text.toString()
              userData.userDOB = etDateOfBirth.text.toString()
              userData.userCity = etCity.text.toString()
              userData.userCountry = etCountry.text.toString()
              userData.userAddressLine1 = etAddressLine1.text.toString()
              userData.userAddressLine2 = etAddressLine2.text.toString()
              userData.userPostCode = etPostCode.text.toString()
              userData.userSignUpMethod = SignUpMethod.EMAIL
              val userInfo = Gson().toJson(userData)
              SharedPrefrencesHelper.write(Constant.USER_INFO , userInfo)
              progressBar.visibility = View.GONE
              findNavController().navigate(R.id.action_signupDetailsFragment_to_mobileNumberFragment)
            } else {
              Toast.makeText(
                requireContext(),
                task.exception?.localizedMessage,
                Toast.LENGTH_LONG
              ).show()
              progressBar.visibility = View.GONE
            }
          }
      }
    } else {
      Toast.makeText(requireContext(), getString(R.string.incomplete_infomation), Toast.LENGTH_SHORT).show()
    }
  }

  private fun showDatePickerDialog() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val dpd = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->

      calendar.set(Calendar.YEAR, year)
      calendar.set(Calendar.MONTH, monthOfYear)
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

      val myFormat = "dd/MM/yyyy" // mention the format you need
      val sdf = SimpleDateFormat(myFormat, Locale.US)
      etDateOfBirth.setText(sdf.format(calendar.time))

    }, year, month, day)

    dpd.show()
  }
}