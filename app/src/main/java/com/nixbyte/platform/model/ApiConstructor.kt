package com.nixbyte.platform.model

import com.google.gson.GsonBuilder
import com.nixbyte.rickandmortymvvm.model.api.API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiConstructor(serverUrl: String
                    ,logLevel: HttpLoggingInterceptor.Level
                    ,callAdapterFactory: CallAdapter.Factory?
                    ,converterFactory: Converter.Factory?
                    ,addingHeaders: MutableList<Header>
                    ,settingHeaders: MutableList<Header>) {

    private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
    .addNetworkInterceptor(object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
            for (header in addingHeaders)
                requestBuilder.addHeader(
                    header.name.utf8().capitalize(),
                    header.value.utf8()
                )
            for (header in settingHeaders)
                requestBuilder.header(header.name.utf8().capitalize(), header.value.utf8())
            val request: Request = requestBuilder.build()
            return chain.proceed(request)
        }
    }).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    private fun getOkHttp() : OkHttpClient {
        return okHttpClient
    }

    private fun getRetrofit() : Retrofit {
        return retrofit
    }

    private constructor(builder: Builder) : this(builder.serverUrl
    ,builder.logLevel
    ,builder.callAdapterFactory
    ,builder.converterFactory
    ,builder.addingHeaders
    ,builder.settingHeaders)

    class Builder(var serverUrl: String = API.DEFAULT_HOST) {

        var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
        private set
        var callAdapterFactory: CallAdapter.Factory? = RxJava2CallAdapterFactory.create()
        private set
        var converterFactory: Converter.Factory? = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        private set
        var addingHeaders: MutableList<Header> = ArrayList()
        private set
        var settingHeaders: MutableList<Header> = ArrayList()
        private set

        fun addHeader(header: Header) = apply {//for multiple value headers like Cache-Control
            addingHeaders.add(header)
        }

        fun setHeader(header: Header) = apply {//for single value headers like Content-Type Authorization etc.
            if(!settingHeaders.contains(header))
                settingHeaders.add(header)
        }

        fun setServerURL( serverUrl: String) = apply {
            this.serverUrl = serverUrl
        }

        fun setLogLevel(logLevel: HttpLoggingInterceptor.Level) = apply {
            this.logLevel = logLevel
        }

        fun setCallAdapterFactory(callAdapterFactory: CallAdapter.Factory?) = apply {
            this.callAdapterFactory = callAdapterFactory
        }

        fun setConverterFactory(converterFactory: Converter.Factory?) = apply {
            this.converterFactory = converterFactory
        }

        fun okhttp() = ApiConstructor(this).getOkHttp()

        fun <T> create(service: Class<T>) = ApiConstructor(this).getRetrofit().create(service)
    }
}