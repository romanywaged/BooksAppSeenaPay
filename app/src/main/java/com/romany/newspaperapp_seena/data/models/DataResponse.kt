package com.romany.newspaperapp_seena.data.models

import com.google.gson.annotations.SerializedName

data class DataResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("copyright")
    val copyright: String,

    @SerializedName("num_results")
    val numResults: Int,

    @SerializedName("results")
    val results: BookResultResponse
)
