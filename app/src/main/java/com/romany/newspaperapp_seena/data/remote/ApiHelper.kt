package com.romany.newspaperapp_seena.data.remote

import javax.inject.Inject

class ApiHelper @Inject constructor(private var apiService: ApiService) {

    suspend fun getAllData() = apiService.getAllData()

}