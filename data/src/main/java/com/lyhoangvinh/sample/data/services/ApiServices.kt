package com.lyhoangvinh.sample.data.services

import com.lyhoangvinh.sample.domain.config.AppConfig
import com.lyhoangvinh.sample.domain.model.CategoriesAvgModel
import retrofit2.http.GET

interface ApiServices {
    @GET(AppConfig.VERSION_1 + AppConfig.CATEGORIES)
    suspend fun getCategories(): CategoriesAvgModel
}