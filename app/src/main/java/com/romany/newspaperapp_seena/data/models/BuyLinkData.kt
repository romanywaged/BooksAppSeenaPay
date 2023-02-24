package com.romany.newspaperapp_seena.data.models

import com.google.gson.annotations.SerializedName

data class BuyLinkData(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)
