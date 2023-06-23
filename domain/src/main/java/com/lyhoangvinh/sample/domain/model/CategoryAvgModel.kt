package com.lyhoangvinh.sample.domain.model


import com.google.gson.annotations.SerializedName

data class CategoryAvgModel(
    @SerializedName("CHID")
    var cHID: String?,
    @SerializedName("category_url")
    var categoryUrl: String?,
    @SerializedName("cover_url")
    var coverUrl: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("shortname")
    var shortname: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("total_videos")
    var totalVideos: Int?
)