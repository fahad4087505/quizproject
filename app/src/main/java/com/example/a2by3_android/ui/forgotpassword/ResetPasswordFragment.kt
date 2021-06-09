package com.example.a2by3_android.ui.forgotpassword

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentResetPasswordBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_reset_password.btnSignIn
import kotlinx.android.synthetic.main.fragment_reset_password.etPassword
import kotlinx.android.synthetic.main.fragment_reset_password.etPasswordLayout
import kotlinx.android.synthetic.main.fragment_reset_password.etRepeatPassword
import kotlinx.android.synthetic.main.fragment_reset_password.etRepeatPasswordLayout


class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding , EmptyRepository>() {

  private val mAuth = FirebaseAuth.getInstance()

  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentResetPasswordBinding {
    return FragmentResetPasswordBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    btnSignIn.setOnClickListener {
      resetUserPasswordAndSignIn()
    }
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun resetUserPasswordAndSignIn() {
    val user = mAuth.currentUser

    if (!TextUtils.isEmpty(etPassword.text.toString()) && !TextUtils.isEmpty(etRepeatPassword.text.toString())) {
      when {
        etPassword.text.toString().length < 8 -> {
          etPasswordLayout.error = "Please Enter at least 8 digits Password"
        }
        etRepeatPassword.text.toString() != etPassword.text.toString() -> {
          etRepeatPasswordLayout.error = "Passwords don't match"
        }
        else -> {
          user?.let {
            it.updatePassword(etPassword.text.toString())
              .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  Toast.makeText(requireContext(), "Password successfully changed", Toast.LENGTH_SHORT).show()
                }
              }
          } ?: Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}