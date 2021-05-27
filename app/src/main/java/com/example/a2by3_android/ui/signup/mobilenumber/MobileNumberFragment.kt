package com.example.a2by3_android.ui.signup.mobilenumber

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentMobileNumberBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_mobile_number.*
import java.util.concurrent.TimeUnit

const val TAG = "MobileNumberFragment"

class MobileNumberFragment : BaseFragment<FragmentMobileNumberBinding, EmptyRepository>() {

  private var mAuth: FirebaseAuth? = null
  private var mVerificationId: String =""
  private val auth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentMobileNumberBinding {
    return FragmentMobileNumberBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    mAuth = FirebaseAuth.getInstance()

    btnGenerateOtp.setOnClickListener {
      if (TextUtils.isEmpty(etPhoneNumber.text.toString())) {
        // when mobile number text field is empty
        // displaying a toast message.
        Toast.makeText(requireContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT)
          .show()
      } else {
        // if the text field is not empty we are calling our
        // send OTP method for getting OTP from Firebase.
        val phone = /*"+92" +*/ etPhoneNumber.text.toString()
        sendVerificationCode(phone)
      }
    }
  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun sendVerificationCode(number: String) {
    val firebaseAuthSettings = mAuth?.firebaseAuthSettings

// Configure faking the auto-retrieval with the whitelisted numbers.
    firebaseAuthSettings?.setAutoRetrievedSmsCodeForPhoneNumber(number, "123456")

    // this method is used for getting
    // OTP on user phone number.
    val options = PhoneAuthOptions.newBuilder(auth)
      .setPhoneNumber(number)
      .setTimeout(30L, TimeUnit.SECONDS)
      .setActivity(requireActivity())
      .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onCodeSent(
          verificationId: String,
          forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
          Log.d(TAG, "onCodeSent: $verificationId ")
          mVerificationId = verificationId
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
          val code = phoneAuthCredential.smsCode
          Log.d(TAG, "onVerificationCompleted: $code")
          if (code != null) {
            tvOtpCode.setText(code)
            verifyCode(code)
          }
        }

        override fun onVerificationFailed(e: FirebaseException) {
          Log.d(TAG, "onVerificationFailed: ${e.message}")
          Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
      })
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

          val user = task.result?.user
        } else {
          // Sign in failed, display a message and update the UI
          Log.d(TAG, "signInWithPhoneAuthCredential Failed: ${task.exception}")
          if (task.exception is FirebaseAuthInvalidCredentialsException) {
            // The verification code entered was invalid
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
          }
          // Update UI
        }
      }
  }
}