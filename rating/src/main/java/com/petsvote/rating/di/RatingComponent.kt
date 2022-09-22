package com.petsvote.rating.di
import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.user.ICheckLocationUserUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.ISaveUserToLocalUseCase
import com.petsvote.rating.RatingFragment
import com.petsvote.rating.RatingTestFragment
import com.petsvote.rating.childFragment.BaseChildFragment
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
    fun inject(fragment: RatingTestFragment)
    fun inject(fragment: BaseChildFragment)

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
    val getRatingUseCase: GetRatingUseCase
    val saveUserUseCase: ISaveUserToLocalUseCase
    val getUserPetsUseCase: IGetUserPetsUseCase
    val getRatingFilterUseCase: IGetRatingFilterUseCase
    val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase
    val ratingFilterTypeUseCase: IGetRatingFilterTypeUseCase
    val checkLocationUserUseCase: ICheckLocationUserUseCase
    val setRatingFilterTypeUseCase: ISetRatingFilterTypeUseCase
    val getRatingFilterTextUseCase: IGetRatingFilterTextUseCase
    val setUserBreedIdInRatingFilterUseCase: ISetBreedsUserPetUseCase
    val setDefaultRatingFilterUseCase: ISetDefaultRatingFilterUseCase
}

val Context.ratingDepsProvider: RatingDepsProvider
    get() = when(this){
        is RatingDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.ratingDepsProvider
    }