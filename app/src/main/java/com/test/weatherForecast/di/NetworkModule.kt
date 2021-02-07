package com.test.weatherForecast.di

import com.test.weatherForecast.network.CityWeatherApi
import com.test.weatherForecast.base.Constants
import com.test.weatherForecast.network.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    factory { AuthInterceptor() }

    factory { provideOkHttpClient(get()) }

    factory { provideForecastApi(get()) }

    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()
}

fun provideForecastApi(retrofit: Retrofit): CityWeatherApi = retrofit.create(CityWeatherApi::class.java)