package com.example.a2by3_android.ui.forgotpassword

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.base.BaseRepository
import com.example.a2by3_android.databinding.FragmentForgotPasswordBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.btnContinue
import kotlinx.android.synthetic.main.fragment_forgot_password.etEmail
import kotlinx.android.synthetic.main.fragment_forgot_password.etEmailLayout


class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding , BaseRepository>() {

  private val mAuth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentForgotPasswordBinding {
    return FragmentForgotPasswordBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): BaseRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    btnContinue.setOnClickListener {
      validateEmail()
    }
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun validateEmail() {
    etEmailLayout.error = null
    if (!TextUtils.isEmpty(etEmail.text.toString())) {
      if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
        mAuth.sendPasswordResetEmail(etEmail.text.toString())
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              Toast.makeText(requireContext(), "Email Successfully Sent", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(requireContext(), task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
          }
      } else {
        etEmailLayout.error = "Please Enter a Valid Email Address"
      }
    }
  }
}