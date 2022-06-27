package com.petsvote.retrofit.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.petsvote.retrofit.SettingsApi
import com.petsvote.retrofit.adapter.NetworkResponseAdapterFactory
import com.petsvote.retrofit.api.ApiInstagram
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.api.UserApi
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

private val contentType = "application/json".toMediaType()
private val json = Json {
    ignoreUnknownKeys = true
}
private val converterFactory = json.asConverterFactory(contentType)


@Module
class RetrofitModule {

    @Provides
    fun provideInstagramRetrofitApi(): ApiInstagram {
        return buildRetrofit(
            getInstagramOkHttpClient(getInstagramHttpLoggingInterceptor()),
            SettingsApi.BASE_URL_INSTAGRAM
        ).create(ApiInstagram::class.java)
    }

    @Provides
    fun provideRatingRetrofitApi(): RatingApi {
        return buildRetrofit(
            getOkHttpClient(getHttpLoggingInterceptor()),
            SettingsApi.BASE_URL
        ).create(RatingApi::class.java)
    }

    @Provides
    fun provideUserRetrofitApi(): UserApi {
        return buildRetrofit(
            getOkHttpClient(getHttpLoggingInterceptor()),
            SettingsApi.BASE_URL
        ).create(UserApi::class.java)
    }

    @Provides
    fun provideConfigurationRetrofitApi(): ConfigurationApi {
        return buildRetrofit(
            getOkHttpClient(getHttpLoggingInterceptor()),
            SettingsApi.BASE_URL
        ).create(ConfigurationApi::class.java)
    }

    fun buildRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(url)
            client(okHttpClient)
            addCallAdapterFactory(NetworkResponseAdapterFactory())
            addConverterFactory(converterFactory)
            build()
        }
    }


    fun getOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient().newBuilder()
        client.apply {
            readTimeout(SettingsApi.READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(SettingsApi.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(SettingsApi.WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        client.addInterceptor(httpLoggingInterceptor).hostnameVerifier(object :HostnameVerifier {
            override fun verify(p0: String?, p1: SSLSession?): Boolean {
                val hv = HttpsURLConnection.getDefaultHostnameVerifier()
                return hv.verify("i.instagram.com", p1)
            }

        })
        client.addInterceptor(httpLoggingInterceptor).hostnameVerifier { p0, p1 ->
            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
            hv.verify(SettingsApi.BASE_URL, p1)
        }

        client.addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().header(SettingsApi.X_HEADER_API_KEY, SettingsApi.X_KEY)
                    .build()
            )
        }.build()

        return client.build()
    }

    fun getInstagramOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient().newBuilder()
        client.apply {
            readTimeout(SettingsApi.READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(SettingsApi.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(SettingsApi.WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        client.addInterceptor(httpLoggingInterceptor).hostnameVerifier(object :HostnameVerifier {
            override fun verify(p0: String?, p1: SSLSession?): Boolean {
                val hv = HttpsURLConnection.getDefaultHostnameVerifier()
                return hv.verify("i.instagram.com", p1)
            }

        })
        client.addInterceptor(httpLoggingInterceptor).hostnameVerifier { p0, p1 ->
            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
            hv.verify("i.instagram.com", p1)
        }


        client.addInterceptor(httpLoggingInterceptor)
        return client.build()
    }

    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    fun getInstagramHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }


}