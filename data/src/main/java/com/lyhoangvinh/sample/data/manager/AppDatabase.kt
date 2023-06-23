package com.lyhoangvinh.sample.data.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lyhoangvinh.sample.domain.model.SampleEntity

@Database(entities = [SampleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase()

