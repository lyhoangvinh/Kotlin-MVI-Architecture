package com.lyhoangvinh.sample.data.injection

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.lyhoangvinh.sample.data.manager.AppDatabase
import com.lyhoangvinh.sample.data.utils.AppConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesRoomDataBase(@ApplicationContext context: Context, gson: Gson): RoomDatabase {
        AppConverters.initialize(gson)
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }


}