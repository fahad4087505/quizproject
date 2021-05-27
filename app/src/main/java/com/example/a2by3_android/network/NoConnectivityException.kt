package com.example.a2by3_android.network

import java.io.IOException

class NoConnectivityException: IOException() {

    override val message: String
    get() = "No internet connection available, please check your connected WiFi or Data"
}