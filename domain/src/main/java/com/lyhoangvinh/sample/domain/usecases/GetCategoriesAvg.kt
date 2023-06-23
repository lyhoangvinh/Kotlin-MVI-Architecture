package com.lyhoangvinh.sample.domain.usecases

import com.lyhoangvinh.sample.domain.repositories.AvgRepo

class GetCategoriesAvg (private val avgRepo: AvgRepo) {
    suspend operator fun invoke() = avgRepo.getCategories().responseAvgModel?.categories
}