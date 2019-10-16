package com.dm.creativerun.network

import com.dm.creativerun.network.service.CategoryService
import com.dm.creativerun.network.service.ProjectService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {

    companion object {
        private const val API_KEY_QUERY_NAME = "api_key"
        private const val TIMEOUT: Long = 10 * 60
    }

    private val retrofit = buildRetrofit()

    val categoryService: CategoryService by lazy {
        retrofit.create(CategoryService::class.java)
    }

    val projectService: ProjectService by lazy {
        retrofit.create(ProjectService::class.java)
    }

    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(getClient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
        .build()

    private fun getMoshi() = Moshi.Builder()
        .build()

    private fun getClient() = OkHttpClient.Builder()
        .addInterceptor(getLoggingInterceptor())
        .addInterceptor(getAuthInterceptor())
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    private fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val urlBuilder = chain.request().url.newBuilder()
            val url = urlBuilder.addQueryParameter(API_KEY_QUERY_NAME, BuildConfig.API_KEY).build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        return logging
    }
}