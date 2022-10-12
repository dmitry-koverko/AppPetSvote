package com.petsvote.vote.entity

sealed class VoteState {
    object Loading: VoteState()
    object ErrorFilter: VoteState()
    object ErrorNoPet: VoteState()
    object FirstPetBonus: VoteState()
    object VoteBonus: VoteState()
    object VoteDefault: VoteState()
}