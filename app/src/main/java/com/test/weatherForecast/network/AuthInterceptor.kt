package com.test.weatherForecast.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url.newBuilder()
            .addQueryParameter("appid", "6a2f65076e7e5ec4c7cdf94fd606f66a")
            .addQueryParameter("units", "metric")
            .build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}