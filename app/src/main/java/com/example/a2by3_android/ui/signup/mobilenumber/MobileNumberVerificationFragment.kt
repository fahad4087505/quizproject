package com.example.a2by3_android.ui.signup.mobilenumber

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentMobileNumberVerificationBinding
import com.example.a2by3_android.helper.SharedPrefrencesHelper
import com.example.a2by3_android.model.User
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_mobile_number_verification.*
import java.util.concurrent.TimeUnit


const val TAG = "MobileNumberFragment"

class MobileNumberVerificationFragment : BaseFragment<FragmentMobileNumberVerificationBinding, EmptyRepository>() {

  private var mAuth: FirebaseAuth? = null
  private var mVerificationId: String =""
  private val args: MobileNumberVerificationFragmentArgs by navArgs()
  private val auth = FirebaseAuth.getInstance()
  private lateinit var firebaseCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentMobileNumberVerificationBinding {
    return FragmentMobileNumberVerificationBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {

    mAuth = FirebaseAuth.getInstance()

    firebaseCallback =  object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

      override fun onCodeSent(
        verificationId: String,
        forceResendingToken: PhoneAuthProvider.ForceResendingToken
      ) {
        Log.d(TAG, "onCodeSent: $verificationId ")
        mVerificationId = verificationId
        progressBar.visibility = View.GONE
      }

      override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
        val code = phoneAuthCredential.smsCode
        Log.d(TAG, "onVerificationCompleted: $code")
      }

      override fun onVerificationFailed(e: FirebaseException) {
        Log.d(TAG, "onVerificationFailed: ${e.message}")
        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
      }
    }


    val mobileNumber = args.mobileNumber
    sendVerificationCode(mobileNumber)

    tvClickHere.setOnClickListener {
      sendVerificationCode(mobileNumber)
    }

    binding.etOtpCode.setOtpCompletionListener { otp ->
      verifyCode(otp)
    }

    /*ivBack.setOnClickListener {
      findNavController().navigateUp()
    }*/
  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun sendVerificationCode(number: String) {
//    val firebaseAuthSettings = mAuth?.firebaseAuthSettings
//
//    // Configure faking the auto-retrieval with the whitelisted numbers.
//    firebaseAuthSettings?.setAutoRetrievedSmsCodeForPhoneNumber(number, "123456")

    // this method is used for getting
    // OTP on user phone number.
    auth.setLanguageCode("en")
    val options = PhoneAuthOptions.newBuilder(auth)
      .setPhoneNumber(number)
      .setTimeout(60L, TimeUnit.SECONDS)
      .setActivity(requireActivity())
      .setCallbacks(firebaseCallback)
      .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
  }

  private fun verifyCode(code: String) {
    // below line is used for getting getting
    // credentials from our verification id and code.
    val credential = PhoneAuthProvider.getCredential(mVerificationId, code)

    // after getting credential we are
    // calling sign in method.
    signInWithPhoneAuthCredential(credential)
  }

  private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
    auth.signInWithCredential(credential)
      .addOnCompleteListener(requireActivity()) { task ->
        if (task.isSuccessful) {
          Log.d(TAG, "signInWithPhoneAuthCredential: ")
          // Sign in success, update UI with the signed-in user's information
          Toast.makeText(requireContext(), "Signed In Successfully", Toast.LENGTH_SHORT).show()

          updateUserInfoWithUserNumber()
        } else {
          // Sign in failed, display a message and update the UI
          Log.d(TAG, "signInWithPhoneAuthCredential Failed: ${task.exception}")
          if (task.exception is FirebaseAuthInvalidCredentialsException) {
            // The verification code entered was invalid
            Toast.makeText(requireContext(), "Invalid Otp Entered", Toast.LENGTH_SHORT).show()
          }
          // Update UI
        }
      }
  }

  private fun updateUserInfoWithUserNumber() {
    val userJson = SharedPrefrencesHelper.read(Constant.USER_INFO , "")
    val userInfo = Gson().fromJson(userJson , User::class.java)
    val user = User()
    user.userUid = userInfo.userUid
    user.userIdToken = userInfo.userIdToken
    user.userEmail = userInfo.userEmail
    user.userPassword = userInfo.userPassword
    user.userDOB = userInfo.userDOB
    user.userCity = userInfo.userCity
    user.userCountry = userInfo.userCountry
    user.userAddressLine1 = userInfo.userAddressLine1
    user.userAddressLine2 = userInfo.userAddressLine2
    user.userPostCode = userInfo.userPostCode
    user.userMobileNumber = args.mobileNumber
    user.userSignUpMethod = userInfo.userSignUpMethod
    val userCompleteInfo = Gson().toJson(user)
    SharedPrefrencesHelper.write(Constant.USER_INFO , userCompleteInfo)
    findNavController().navigate(R.id.action_mobileNumberVerificationFragment_to_signUpSuccessFragment)
  }
}