package com.petsvote.vote.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import com.petsvote.vote.fragments.state.BonusVoteFragment
import com.petsvote.vote.fragments.VoteFragment
import com.petsvote.vote.fragments.state.ItemVoteFragment
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class VoteScope

@[VoteScope Component(
    dependencies = [VoteDeps::class],
    modules = [VoteModule::class])]

interface VoteComponent {

    fun inject(fragment: VoteFragment)
    fun injectAddVote(itemVoteFragment: ItemVoteFragment)
    fun injectBonusVote(bonusVoteFragment: BonusVoteFragment) {

    }

    @Component.Builder
    interface Builder{

        fun deps(ratingDeps: VoteDeps): Builder
        fun build(): VoteComponent

    }

}

@Module
internal class VoteModule{

}

interface VoteDepsProvider {

    var depsVote: VoteDeps

}

interface VoteDeps{

    val votePetsUseCase: IGetVotePetsUseCase
    val addVoteUseCase: IAddVoteUseCase
    val userPetsUserCase: IGetUserPetsUseCase
    val getUserUseCase: IGetUserUseCase
    val getStringResourcesUseCaseImpl: GetStringResourcesUseCase

}

val Context.voteDepsProvider: VoteDepsProvider
    get() = when(this){
        is VoteDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.voteDepsProvider
    }