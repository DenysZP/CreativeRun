package com.dm.creativerun.network.service

import com.dm.creativerun.network.response.ProjectResponse
import com.dm.creativerun.network.response.ProjectsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {

    @GET("v2/projects")
    fun getProjectsAsync(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("q") query: String?,
        @Query("sort") sort: String?,
        @Query("time") period: String?,
        @Query("field") category: String?,
        @Query("color_hex") colorHex: String?
    ): Deferred<ProjectsResponse>

    @GET("v2/projects/{id}")
    fun getProjectDetailsByIdAsync(@Path("id") id: Long): Deferred<ProjectResponse>

    @GET("v2/projects/{id}/comments")
    fun getCommentsOfProjectAsync(@Path("id") id: String): Deferred<Any>
}