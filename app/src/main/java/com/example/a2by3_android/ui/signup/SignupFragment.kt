package com.example.a2by3_android.ui.signup

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignupBinding
import com.example.a2by3_android.enum.SignUpMethod
import com.example.a2by3_android.helper.SharedPrefrencesHelper
import com.example.a2by3_android.model.User
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant
import com.example.a2by3_android.util.Constant.GOOGLE_SIGN_IN_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_signup.btnEmailSignUp
import kotlinx.android.synthetic.main.fragment_signup.btnGoogleSignUp
import kotlinx.android.synthetic.main.fragment_signup.tvSignIn

const val TAG = "SignUpFragment"

class SignupFragment : BaseFragment<FragmentSignupBinding, EmptyRepository>() {

  private val auth: FirebaseAuth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSignupBinding {
    return FragmentSignupBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    btnGoogleSignUp.setOnClickListener {
      signIn()
    }
    btnEmailSignUp.setOnClickListener {
      findNavController().navigate(R.id.action_signupFragment_to_signupDetailsFragment)
    }
    tvSignIn.setOnClickListener {
      findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun signIn() {
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      // Client Id type 3 from google services.json file
      .requestIdToken("628095905587-fi9qb5bik5v4ige5n1v3f99iaeu6oete.apps.googleusercontent.com")
      .requestEmail()
      .build()
    val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    val signInIntent: Intent = mGoogleSignInClient.signInIntent
    startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == GOOGLE_SIGN_IN_CODE) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
      handleSignInResult(task)
    }
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)

      // Signed in successfully, show authenticated UI.
      account?.idToken?.let { firebaseAuthWithGoogle(it) } ?: kotlin.run {
        val userData = User()
        userData.userUid = account?.id
        userData.userEmail = account?.email
        userData.userSignUpMethod = SignUpMethod.GOOGLE
        val userInfo = Gson().toJson(userData)
        SharedPrefrencesHelper.write(Constant.USER_INFO, userInfo)
        findNavController().navigate(R.id.action_signupFragment_to_mobileNumberFragment)
      }
    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
    }
  }

  private fun firebaseAuthWithGoogle(idToken: String) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
      .addOnCompleteListener(requireActivity()) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          Log.d(TAG, "signInWithCredential:success")
          val user = auth.currentUser
          val userData = User()
          userData.userUid = user?.uid
          userData.userEmail = user?.email
          userData.userIdToken = idToken
          userData.userSignUpMethod = SignUpMethod.GOOGLE
          val userInfo = Gson().toJson(userData)
          SharedPrefrencesHelper.write(Constant.USER_INFO, userInfo)
          findNavController().navigate(R.id.action_signupFragment_to_mobileNumberFragment)
        } else {
          // If sign in fails, display a message to the user.
          Log.w(TAG, "signInWithCredential:failure", task.exception)
        }
      }
  }
}