package com.example.a2by3_android.network

import com.example.a2by3_android.base.BaseErrorResponse

/*
* A helper class which helps in encapsulating repository responses based on response state (could be success/error)
* Also used in fragments to display values based on states
*
* Example
* A Fragment observing a LiveData object and updates it's views whatever API state is error or success.
* */

data class Resource<out T>(val status: Status, val  data: T?, val message: String?, val responseError: BaseErrorResponse?= null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, data: T? = null, responseError: BaseErrorResponse): Resource<T> {
            return Resource(Status.ERROR, data, message, responseError)
        }


        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}