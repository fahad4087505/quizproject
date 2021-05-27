package com.example.a2by3_android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.example.a2by3_android.MyApplication

object Util {

  fun startNetworkCallback() {
    val cm: ConnectivityManager =
      MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val builder: NetworkRequest.Builder = NetworkRequest.Builder()

    cm.registerNetworkCallback(
      builder.build(),
      object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
          Constant.Variables.isNetworkConnected = true
        }

        override fun onLost(network: Network) {
          Constant.Variables.isNetworkConnected = false
        }
      })


  }
}