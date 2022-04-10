package com.petsvote.app.di

import android.app.Application
import android.content.res.Configuration
import com.petsvote.data.di.DataModule
import com.petsvote.domain.di.UserUseCaseModule
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import com.petsvote.legal.di.TermsDeps
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
    modules = [AppModule::class, DataModule::class, UserUseCaseModule::class, RetrofitModule::class,
        RoomModule::class]
)]
interface AppComponent : SplashDeps, RegisterDeps, RoomDeps, TermsDeps {

    override val registerUserUseCase: RegisterUserUseCase
    override val checkLoginUserUseCase: CheckLoginUserUseCase
    override val saveUserUseCase: SaveUserToLocalUseCase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideConfiguration(application: Application): Configuration{
        return application.resources.configuration
    }

//    @Provides
//    @AppScope
//    fun providesRoomRepository(application: Application): RoomRepository {
//        return RoomRepository(application)
//    }
}

@Scope
annotation class AppScope