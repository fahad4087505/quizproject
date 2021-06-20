package com.example.triviaquizapp.network

import com.example.triviaquizapp.util.Constant.Variables.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response

class NoInternetInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isNetworkConnected){
            throw NoConnectivityException()
        } else{
            chain.proceed(chain.request())
        }
    }
}