package com.lyhoangvinh.sample.domain.model

import com.google.gson.annotations.SerializedName

data class CategoriesAvgModel(
    @SerializedName("response")
    var responseAvgModel: ResponseAvgModel?,
    @SerializedName("success")
    var success: Boolean?
)