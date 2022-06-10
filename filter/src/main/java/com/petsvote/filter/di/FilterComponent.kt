package com.petsvote.filter.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.filter.*
import com.petsvote.filter.FilterFragment
import com.petsvote.filter.breeds.SelectBreedsFragment
import com.petsvote.filter.kinds.SelectKindsFragment
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class FilterScope

@[FilterScope Component(
    dependencies = [FilterDeps::class],
    modules = [FilterModule::class])]
interface FilterComponent {

    fun inject(filterFragment: FilterFragment)
    fun injectSelectKinds(selectKindsFragment: SelectKindsFragment)
    fun injectSelectBreeds(selectBreedsFragment: SelectBreedsFragment)

    @Component.Builder
    interface Builder{

        fun deps(filterDeps: FilterDeps): Builder
        fun build(): FilterComponent

    }

}

@Module
internal class FilterModule{

}

interface FilterDepsProvider {

    var depsFilter: FilterDeps

}

interface FilterDeps{
    val ratingFilterTypeUseCase: IGetRatingFilterTypeUseCase
    val kindsUseCase: IGetKindsUseCase
    val ratingFilterUseCase: IGetRatingFilterUseCase
    val setKindsUseCase: ISetKindsRatingFilterUseCase
    val breedsUseCase: IGetBreedsUseCase
    val setBreedUseCase: ISetBreedUseCase
    val filterUseCase: IGetFilterUseCase
    val setSexFilterUseCase: ISetSexUseCase
    val breedsPagingUseCase: IGetBreedsPagingUseCase
    val setMaxAgeUseCase: ISetMaxAgeUseCase
    val setMinAgeUseCase: ISetMinAgeUseCase
}

val Context.filterDepsProvider: FilterDepsProvider
    get() = when(this){
        is FilterDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.filterDepsProvider
    }