package com.petsvote.app.di

import android.app.Application
import com.petsvote.data.di.DataModule
import com.petsvote.domain.di.UseCaseModule
import com.petsvote.domain.usecases.RegisterUserUseCase
import com.petsvote.register.di.RegisterDeps
import com.petsvote.retrofit.di.RetrofitModule
import com.petsvote.room.RoomDeps
import com.petsvote.room.RoomModule
import com.petsvote.splash.di.SplashDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@[AppScope Component(
    modules = [AppModule::class, DataModule::class, UseCaseModule::class, RetrofitModule::class,
        RoomModule::class]
)]
interface AppComponent : SplashDeps, RegisterDeps, RoomDeps {

    override val registerUserUseCase: RegisterUserUseCase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}

@Module
class AppModule {

//    @Provides
//    @AppScope
//    fun provideApplication(application: Application) = application

//    @Provides
//    @AppScope
//    fun providesRoomRepository(application: Application): RoomRepository {
//        return RoomRepository(application)
//    }
}

@Scope
annotation class AppScope