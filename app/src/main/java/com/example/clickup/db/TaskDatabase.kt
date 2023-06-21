package com.example.clickup.db

import androidx.room.*
import com.example.clickup.model.CategoryInfo
import com.example.clickup.model.TaskInfo

@Database(entities = [TaskInfo::class, CategoryInfo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskCategoryDao(): TaskCategoryDao
}