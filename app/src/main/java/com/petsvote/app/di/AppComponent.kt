package com.petsvote.app.di

import android.app.Application
import com.petsvote.data.di.DataModule
import com.petsvote.domain.di.UseCaseModule
import com.petsvote.domain.usecases.RegisterUserUseCase
import com.petsvote.retrofit.di.RetrofitModule
import com.petsvote.splash.di.SplashDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@[AppScope Component(
    modules = [AppModule::class, DataModule::class, UseCaseModule::class, RetrofitModule::class])]
interface AppComponent: SplashDeps {

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
//    fun provideUserRemoteRepository(userApi: UserApi): UserRemoteRepository{
//        return com.petsvote.data.repository.UserRemoteRepository(userApi = userApi)
//    }
//
//    @Provides
//    @AppScope
//    fun provideRegisterUserUseCase(userRemoteRepository: UserRemoteRepository): RegisterUserUseCase{
//        return RegisterUserUseCaseImpl(userRemoteRepository = userRemoteRepository)
//    }

//    @Provides
//    @AppScope
//    fun providesRoomRepository(application: Application): RoomRepository {
//        return RoomRepository(application)
//    }
}

@Scope
annotation class AppScope