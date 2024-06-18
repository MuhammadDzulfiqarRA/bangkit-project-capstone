package com.dicoding.abai.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        private const val BASE_URL = "http://:3000/api/v1/"

        private fun createApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS) // Waktu maksimum untuk koneksi ke server
                .readTimeout(30, TimeUnit.SECONDS)    // Waktu maksimum untuk membaca respons dari server
                .writeTimeout(30, TimeUnit.SECONDS)   // Waktu maksimum untuk menulis ke server
                .retryOnConnectionFailure(true)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }


        fun getApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    // Menambahkan header Content-Type: application/json
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }


        fun getApiServiceForUser(): ApiService {
            return createApiService()
        }

        fun getApiServiceDetailStory(): ApiService {
            return createApiService()
        }

        fun getApiServiceFollower(): ApiService {
            return createApiService()
        }

        fun getApiServiceFollowing(): ApiService {
            return createApiService()
        }
    }
}