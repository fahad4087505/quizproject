package com.example.a2by3_android.ui.signup

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignupBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant.GOOGLE_SIGN_IN_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_signup.*

const val TAG = "SignUpFragment"

class SignupFragment : BaseFragment<FragmentSignupBinding, EmptyRepository>() {
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
  }

  override fun onOptionsSelected(item: MenuItem) {

  }

  override fun onActivityCreation() {

  }

  private fun signIn() {
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail()
      .build()
    val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    val signInIntent: Intent = mGoogleSignInClient.signInIntent
    startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == GOOGLE_SIGN_IN_CODE) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
      handleSignInResult(task)
    }
  }

  private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
      val account = completedTask.getResult(ApiException::class.java)

      // Signed in successfully, show authenticated UI.
      Toast.makeText(requireContext(), account?.email, Toast.LENGTH_SHORT).show()
    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
    }
  }
}