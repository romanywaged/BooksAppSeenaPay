package com.romany.newspaperapp_seena.data.models

import com.google.gson.annotations.SerializedName

data class BestSellerBooksResponse(

    @SerializedName("list_id")
    val listId: Int,

    @SerializedName("list_name")
    val listName: String,

    @SerializedName("list_name_encoded")
    val listNameEncoded: String,

    @SerializedName("display_name")
    val displayName: String,

    @SerializedName("updated")
    val updated: String,

    @SerializedName("list_image")
    val listImage: String?,

    @SerializedName("list_image_width")
    val listImageWidth: Int?,

    @SerializedName("list_image_height")
    val listImageHeight: Int?,

    @SerializedName("books")
    val books: List<BookData>
)
