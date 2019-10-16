package com.dm.creativerun.network.response.data

import com.dm.creativerun.domain.entity.User
import com.dm.creativerun.network.response.extension.getBiggerImage
import com.dm.creativerun.network.response.extension.getSmallerImage
import com.squareup.moshi.Json

internal class UserData(
    private val id: Long,
    @field: Json(name = "display_name") private val name: String,
    private val location: String,
    private val company: String,
    private val occupation: String,
    private val url: String,
    private val images: Map<String, String>
) {

    fun mapToEntity(): User {
        return User(
            id,
            name,
            location,
            company,
            occupation,
            url,
            images.getBiggerImage(),
            images.getSmallerImage()
        )
    }
}