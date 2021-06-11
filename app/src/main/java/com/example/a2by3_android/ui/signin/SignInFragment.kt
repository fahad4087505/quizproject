package com.example.a2by3_android.ui.signin

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.R
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentSignInBinding
import com.example.a2by3_android.extensions.hide
import com.example.a2by3_android.extensions.show
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.ui.signup.TAG
import com.example.a2by3_android.util.Constant
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.btnGoogleSignIn
import kotlinx.android.synthetic.main.fragment_sign_in.btnSignIn
import kotlinx.android.synthetic.main.fragment_sign_in.etEmail
import kotlinx.android.synthetic.main.fragment_sign_in.etPassword
import kotlinx.android.synthetic.main.fragment_sign_in.progressBar
import kotlinx.android.synthetic.main.fragment_sign_in.tvForgotPassword


class SignInFragment : BaseFragment<FragmentSignInBinding, EmptyRepository>() {

  private val mAuth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSignInBinding {
    return FragmentSignInBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    btnSignIn.setOnClickListener {
      userSignIn()
    }

    btnGoogleSignIn.setOnClickListener {
      signIn()
    }

    tvForgotPassword.setOnClickListener {
      findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
    }
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun userSignIn() {
    if (!TextUtils.isEmpty(etEmail.text.toString()) && !TextUtils.isEmpty(etPassword.text.toString())) {
      progressBar.show()
      mAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
        .addOnCompleteListener { task ->
          if (task.isSuccessful) {
            Toast.makeText(
              requireContext(),
              task.result?.user?.uid,
              Toast.LENGTH_LONG
            ).show()
            progressBar.hide()
            findNavController().navigate(R.id.action_signInFragment_to_sellingProductListFragment)
          } else {
            Toast.makeText(
              requireContext(),
              task.exception?.localizedMessage,
              Toast.LENGTH_LONG
            ).show()
            progressBar.hide()
          }
        }
    }
  }

  private fun signIn() {
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken("628095905587-fi9qb5bik5v4ige5n1v3f99iaeu6oete.apps.googleusercontent.com")
      .requestEmail()
      .build()
    val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    val signInIntent: Intent = mGoogleSignInClient.signInIntent
    startActivityForResult(signInIntent, Constant.GOOGLE_SIGN_IN_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == Constant.GOOGLE_SIGN_IN_CODE) {
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
      findNavController().navigate(R.id.action_signInFragment_to_sellingProductListFragment)

    } catch (e: ApiException) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.statusCode)
    }
  }
}