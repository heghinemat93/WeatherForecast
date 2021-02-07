package com.test.weatherForecast.network

data class ApiResult<out T>(
    val status: Status,
    val data: T?,
    val exception: Exception?
) {
    companion object {
        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(exception: Exception?): ApiResult<T> {
            return ApiResult(
                Status.ERROR,
                null,
                exception
            )
        }

        fun <T> loading(data: T? = null): ApiResult<T> {
            return ApiResult(
                Status.LOADING,
                data,
                null
            )
        }
    }
}