package com.example.a2by3_android.util

import kotlin.properties.Delegates

object Constant {

  object Variables {
    var isNetworkConnected: Boolean by Delegates.observable(false) { _, _, _ ->
    }
  }

  const val GOOGLE_SIGN_IN_CODE = 5
  const val REQUEST_IMAGE_CAPTURE = 14
  const val REQUEST_PICK_IMAGE = 12
  const val CAMERA_PERMISSION_REQUEST_CODE = 72
  const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1024
  const val AUTH_FAILURE_CODE = 401
  const val AUTH_URL_ERROR = ".medznmore.com/oauth/token"
  const val SESSION_EXPIRED = "Your session has been expired"
  const val SOMETHING_WENT_WRONG = "Something went wrong"
  const val GENERAL_ERROR_CODE = -1
  const val REASON_FAILURE = "Failure"
  const val USER_INFO = "userInfo"

}