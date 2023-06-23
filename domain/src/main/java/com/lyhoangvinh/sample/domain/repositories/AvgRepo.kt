package com.lyhoangvinh.sample.domain.repositories

import com.lyhoangvinh.sample.domain.model.CategoriesAvgModel

interface AvgRepo {
    suspend fun getCategories(): CategoriesAvgModel
}