package com.petsvote.vote.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.vote.ItemVoteFragment
import com.petsvote.vote.VoteFragment
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

}

val Context.voteDepsProvider: VoteDepsProvider
    get() = when(this){
        is VoteDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.voteDepsProvider
    }