package com.codinginflow.mvvmtodo.di

import android.app.Application
import androidx.room.Room
import com.codinginflow.mvvmtodo.data.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(
        app:Application,
        callback: TasksDatabase.callback
    )= Room.databaseBuilder(app, TasksDatabase::class.java,"task_database")
            .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun providesTasksDao(db: TasksDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun  providesApplicationScope()= CoroutineScope(SupervisorJob())
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope