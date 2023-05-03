package com.example.roomtodo.networks.JsonAdapter

import com.example.roomtodo.Utils.Extensions.Constants
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    companion object {

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    },
                )
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader(
                            "Authorization",
                            "Bearer 34856940ef4008bade04e8038e9905cee7deb297256f8d6c2a5f38a0c58e2d22",
                        )
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        fun getRetrofit(moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .client(buildClient())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(Constants.SERVER_BASEURL)
                .build()
        }
    }
}