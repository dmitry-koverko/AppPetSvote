package com.petsvote.rating.di
import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import com.petsvote.rating.RatingFragment
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class RatingScope

@[RatingScope Component(
    dependencies = [RatingDeps::class],
    modules = [RatingModule::class])]
interface RatingComponent {

    fun inject(fragment: RatingFragment)

    @Component.Builder
    interface Builder{

        fun deps(ratingDeps: RatingDeps): Builder
        fun build(): RatingComponent

    }

}

@Module
internal class RatingModule{

}

interface RatingDepsProvider {

    var depsRating: RatingDeps

}

interface RatingDeps{
    val registerUserUseCase: RegisterUserUseCase
    val saveUserUseCase: SaveUserToLocalUseCase
}

val Context.ratingDepsProvider: RatingDepsProvider
    get() = when(this){
        is RatingDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.ratingDepsProvider
    }