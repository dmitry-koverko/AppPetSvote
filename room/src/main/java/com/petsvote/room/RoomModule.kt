package com.petsvote.room

import android.app.Application
import android.content.Context
import com.petsvote.room.dao.RatingFilterDao
import com.petsvote.room.dao.UserDao
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(
    dependencies = [RoomDeps::class],
    modules = [RoomModule::class])
interface RegisterComponent {

    @Component.Builder
    interface Builder{

        fun deps(roomDeps: RoomDeps): Builder
        fun build(): RegisterComponent

    }

}


interface RoomDepsProvider {

    var depsRoom: RoomDeps

}

interface RoomDeps{
    val application: Application
}

val Context.roomDepsProvider: RoomDepsProvider
    get() = when(this){
        is RoomDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.roomDepsProvider
    }

@Module
class RoomModule {

    @Provides
    fun provideUserDao(application: Application): UserDao{
        return AppDatabase.getDatabase(application).userDao()
    }

    @Provides
    fun provideRatingFilterDao(application: Application): RatingFilterDao{
        return AppDatabase.getDatabase(application).ratingFilterDao()
    }

}
