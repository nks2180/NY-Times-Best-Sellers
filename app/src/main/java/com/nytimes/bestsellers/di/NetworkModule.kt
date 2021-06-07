package com.nytimes.bestsellers.di

import android.util.Log
import com.nytimes.bestsellers.BuildConfig
import com.nytimes.bestsellers.categories.api.ApiBackend
import com.nytimes.bestsellers.categories.api.ApiFetcher
import com.nytimes.bestsellers.common.AppConstants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 30L
private const val HTTP_LOGGING_TAG = "HttpLoggingInterceptor"

val networkModule = module {

    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { moshi() }
    factory { provideRetrofitBuilder(get(), get()) }
    factory { bestSellerCategories(get()) }
    factory { ApiFetcher(get()) }
}

fun getBaseUrl(): String {
    return BASE_URL
}

fun moshi(): Moshi {
    return JsonDefaults.moshi()
}

fun provideRetrofitBuilder(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit.Builder {

    return Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
}

internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    httpClientBuilder.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
    return httpClientBuilder.build()
}

internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d(HTTP_LOGGING_TAG, message) }
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}

internal fun bestSellerCategories(retrofit: Retrofit.Builder): ApiBackend {
    return retrofit
        .baseUrl(BASE_URL)
        .build()
        .create(ApiBackend::class.java)
}



