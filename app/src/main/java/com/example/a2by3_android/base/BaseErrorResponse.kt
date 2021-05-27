package com.example.a2by3_android.base

import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("message") val errorMessage: String,
        @SerializedName("status") val status: String
)
