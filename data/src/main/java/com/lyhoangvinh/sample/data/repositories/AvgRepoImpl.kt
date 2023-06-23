package com.lyhoangvinh.sample.data.repositories

import com.lyhoangvinh.sample.data.services.ApiServices
import com.lyhoangvinh.sample.domain.model.CategoriesAvgModel
import com.lyhoangvinh.sample.domain.repositories.AvgRepo
import javax.inject.Inject

class AvgRepoImpl @Inject constructor(private val apiServices: ApiServices): AvgRepo {

    override suspend fun getCategories(): CategoriesAvgModel = apiServices.getCategories()
}