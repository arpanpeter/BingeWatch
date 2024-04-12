package com.example.bingewatch.models

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id") val movieId: Long,
    @SerializedName("cast") val cast: List<Cast>
)