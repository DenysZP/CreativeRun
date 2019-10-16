package com.dm.creativerun.network.service

import com.dm.creativerun.network.response.CategoriesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CategoryService {

    @GET("v2/fields")
    fun getCategoriesAsync(): Deferred<CategoriesResponse>
}