package com.lyhoangvinh.sample.data.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lyhoangvinh.sample.data.BuildConfig
import com.lyhoangvinh.sample.data.network.makeService
import com.lyhoangvinh.sample.data.services.ApiServices
import com.lyhoangvinh.sample.domain.config.AppConfig.AVG_LE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideGSon(): Gson =
        GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().followRedirects(true).followSslRedirects(true)
            .retryOnConnectionFailure(true).cache(null)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(gSon: Gson, okHttpClient: OkHttpClient): ApiServices =
        makeService(
            ApiServices::class.java, gSon, okHttpClient, AVG_LE_URL
        )

}