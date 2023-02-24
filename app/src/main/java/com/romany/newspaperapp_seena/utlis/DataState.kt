package com.romany.newspaperapp_seena.utlis

import com.romany.newspaperapp_seena.data.models.DataResponse


sealed class DataState{

    object Loading : DataState()

    class  Failure(val msg:Throwable) : DataState()

    object Empty : DataState()

    class Success(val dataResponse: DataResponse) : DataState()

}