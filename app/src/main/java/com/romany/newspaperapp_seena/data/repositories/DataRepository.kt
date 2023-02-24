package com.romany.newspaperapp_seena.data.repositories

import com.romany.newspaperapp_seena.data.models.DataResponse
import com.romany.newspaperapp_seena.data.remote.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(private var apiHelper: ApiHelper) {

    suspend fun getAllData() :Flow<DataResponse> = flow {
        emit(apiHelper.getAllData())
    }.flowOn(Dispatchers.IO)

}