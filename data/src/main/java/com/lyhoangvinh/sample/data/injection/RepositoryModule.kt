package com.lyhoangvinh.sample.data.injection

import com.lyhoangvinh.sample.data.repositories.AvgRepoImpl
import com.lyhoangvinh.sample.domain.repositories.AvgRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun avgRepoImpl(repoImpl: AvgRepoImpl): AvgRepo
}