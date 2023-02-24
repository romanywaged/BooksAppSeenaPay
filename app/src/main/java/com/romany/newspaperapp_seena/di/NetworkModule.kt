package com.romany.newspaperapp_seena.di

import com.romany.newspaperapp_seena.BuildConfig
import com.romany.newspaperapp_seena.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun providesApiService(url:String) : ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}