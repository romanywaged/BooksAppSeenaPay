package com.romany.newspaperapp_seena.data.remote

import com.romany.newspaperapp_seena.BuildConfig
import com.romany.newspaperapp_seena.data.models.DataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("lists/full-overview.json")
    suspend fun getAllData(@Query ("api-key")
                               apiKey:String = BuildConfig.API_KEY) : DataResponse

}