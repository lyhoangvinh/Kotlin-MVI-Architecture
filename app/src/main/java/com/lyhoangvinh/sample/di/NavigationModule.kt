package com.lyhoangvinh.sample.di

import com.lyhoangvinh.sample.navigator.NavigatorHelper
import com.lyhoangvinh.sample.navigator.composenavigator.MyNavigatorHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

  @Binds
  @Singleton
  abstract fun provideComposeNavigator(composeNavigator: MyNavigatorHelper): NavigatorHelper
}
