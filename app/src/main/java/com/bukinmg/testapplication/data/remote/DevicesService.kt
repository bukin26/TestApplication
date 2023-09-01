package com.bukinmg.testapplication.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DevicesService {

    @GET("test_android/items.test")
    suspend fun getDevices(): Response<DevicesResponse>


    companion object {

        private const val BASE_URL = "https://veramobile.mios.com/"

        fun create(): DevicesService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DevicesService::class.java)
        }
    }
}