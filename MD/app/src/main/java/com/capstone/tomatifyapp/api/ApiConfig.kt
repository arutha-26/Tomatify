package com.capstone.tomatifyapp.api

import com.capstone.tomatifyapp.utils.TIMEOUT_REQUEST
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
    @InstallIn(SingletonComponent::class)
    object ApiConfig {

        @JvmField
        val BASE_URL = "https://story-api.dicoding.dev/v1/"

        @JvmField
        val NEWS_URL = "https://news2-dot-deploynwes.et.r.appspot.com/"

        @Provides
        fun getApiService(@Named("baseURL") baseURL: String): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient
                .Builder()
                .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }

        @Provides
        @Named("baseURL")
        fun provideBaseUrl(): String {
            return BASE_URL
        }
    }

