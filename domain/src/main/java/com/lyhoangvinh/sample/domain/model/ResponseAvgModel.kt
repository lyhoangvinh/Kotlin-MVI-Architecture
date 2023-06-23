package com.lyhoangvinh.sample.domain.model


import com.google.gson.annotations.SerializedName

data class ResponseAvgModel(
    @SerializedName("categories")
    var categories: List<CategoryAvgModel>?
)