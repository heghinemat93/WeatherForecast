package com.test.weatherForecast.network

import org.koin.android.BuildConfig


open class ResponseHandler {

    fun <T : Any?> handleSuccess(data: T): ApiResult<T> {
        return ApiResult.success(data)
    }

    fun <T : Any?> handleException(e: Exception): ApiResult<T> {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
        return ApiResult.error(e)
    }
}