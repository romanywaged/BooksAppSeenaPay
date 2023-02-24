package com.romany.newspaperapp_seena.ui.listeners

import com.romany.newspaperapp_seena.data.models.BookData

interface IBookClickedListener {

    fun onBookClicked(bookData: BookData)

}