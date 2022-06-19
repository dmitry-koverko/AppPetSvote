package com.petsvote.app.di

import android.app.Application
import android.app.Dialog
import android.content.res.Configuration
import android.content.res.Resources
import com.petsvote.data.di.DataModule
import com.petsvote.dialog.di.DialogDeps
import com.petsvote.domain.di.*
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.user.*
import com.petsvote.filter.di.FilterDeps
import com.petsvote.legal.di.TermsDeps
import com.petsvote.rating.di.RatingDeps
import com.petsvote.register.di.RegisterDeps
import com.petsvote.retrofit.di.RetrofitModule
import com.petsvote.room.RoomDeps
import com.petsvote.room.RoomModule
import com.petsvote.splash.di.SplashDeps
import com.petsvote.ui.di.UIModule
import com.petsvote.user.di.UserDeps
import com.petsvote.vote.di.VoteDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@[AppScope Component(
    modules = [AppModule::class, DataModule::class, UserUseCaseModule::class, RetrofitModule::class,
        RoomModule::class, ConfigurationModule::class, UIModule::class, ResourcesModule::class,
        RatingModule::class, FilterModule::class]
)]
interface AppComponent : SplashDeps, RegisterDeps, RoomDeps, TermsDeps, RatingDeps, VoteDeps,
    UserDeps, FilterDeps, DialogDeps {

    override val registerUserUseCase: IRegisterUserUseCase
    override val checkLoginUserUseCase: ICheckLoginUserUseCase
    override val saveUserUseCase: ISaveUserToLocalUseCase
    override val getUserAgreementUseCase: GetUserAgreementUseCase
    override val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase
    override val getStringResourcesUseCase: GetStringResourcesUseCase
    override val getRatingUseCase: GetRatingUseCase
    override val getUserPetsUseCase: IGetUserPetsUseCase
    override val getRatingFilterUseCase: IGetRatingFilterUseCase
    override val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase
    override val setDefaultRatingFilterUseCase: ISetDefaultRatingFilterUseCase
    override val ratingFilterTypeUseCase: IGetRatingFilterTypeUseCase
    override val checkLocationUserUseCase: ICheckLocationUserUseCase
    override val setRatingFilterTypeUseCase: ISetRatingFilterTypeUseCase
    override val votePetsUseCase: IGetVotePetsUseCase
    override val addVoteUseCase: IAddVoteUseCase
    override val kindsUseCase: IGetKindsUseCase
    override val breedsUseCase: IGetBreedsUseCase
    override val setBreedUseCase: ISetBreedUseCase
    override val filterUseCase: IGetFilterUseCase
    override val setSexFilterUseCase: ISetSexUseCase
    override val breedsPagingUseCase: IGetBreedsPagingUseCase
    override val setMaxAgeUseCase: ISetMaxAgeUseCase
    override val setMinAgeUseCase: ISetMinAgeUseCase
    override val currentUser: IGetUserUseCase
    override val settingsNotifyUseCase: IGetSettingsNotifyUseCase
    override val setSettingsNotifyUseCase: ISetSettingsNotifyUseCase
    override val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase
    override val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase
    override val setImageUseCase: ISetImageUseCase
    override val getImagesUseCase: IGetImagesUseCase
    override val setImageCropUseCase: ISetImageCropUseCase

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
    fun provideConfiguration(application: Application): Configuration {
        return application.resources.configuration
    }

}

@Scope
annotation class AppScope