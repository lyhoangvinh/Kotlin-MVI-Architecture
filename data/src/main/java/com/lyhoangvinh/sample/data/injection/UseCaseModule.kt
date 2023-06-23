package com.lyhoangvinh.sample.data.injection

import com.lyhoangvinh.sample.domain.repositories.AvgRepo
import com.lyhoangvinh.sample.domain.usecases.GetCategoriesAvg
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetCategoriesAvg(repo: AvgRepo) =
        GetCategoriesAvg(repo)
}