package com.example.bingewatch.models

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("character") val character: String,
    @SerializedName("profile_path") val profilePath: String?
)