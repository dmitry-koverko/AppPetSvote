package com.petsvote.room

import android.app.Application
import com.petsvote.room.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideUserDao(application: Application): UserDao{
        return AppDatabase.getDatabase(application).userDao()
    }

}