package com.example.triviaquizapp.model

import com.example.triviaquizapp.enum.SignUpMethod

data class User (
  var userUid: String ?= null,
  var userEmail: String ?= null,
  var userPassword: String ?= null,
  var userDOB: String ?= null,
  var userCountry: String ?= null,
  var userCity: String ?= null,
  var userAddressLine1: String ?= null,
  var userAddressLine2: String ?= null,
  var userPostCode: String ?= null,
  var userMobileNumber: String ?= null,
  var userIdToken: String ?= null,
  var userSignUpMethod: SignUpMethod ?= null
)