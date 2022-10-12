package com.petsvote.vote.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.vote.entity.VoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class VoteViewModel @Inject constructor(
    private val votePetsUseCase: IGetVotePetsUseCase,

) : BaseViewModel() {

    var pets = MutableStateFlow<VotePet?>(null)
    var state = MutableStateFlow<VoteState>(VoteState.Loading)

    private var isStateFilter = false
    private var listForVote = mutableListOf<VotePet>()

    fun getRatingFirst(){
        viewModelScope.launch {
            votePetsUseCase.getRating().collect {
                when{
                    it.isEmpty() && !isStateFilter -> state.emit(VoteState.ErrorFilter)
                    it.isEmpty() && isStateFilter -> state.emit(VoteState.ErrorNoPet)
                    else -> {
                        isStateFilter = true
                        listForVote.addAll(it)
                        listForVote.removeAt(0)
                        if(listForVote.isNotEmpty() && listForVote.first().cardType == 2){
                            state.emit(VoteState.VoteBonus)
                            pets.emit(listForVote.first())
                        }else{
                            pets.emit(listForVote.first())
                            state.emit(VoteState.VoteDefault)
                        }
                    }
                }
            }
        }
    }

    fun getRatingLoad(){

    }

    fun getRatingReLoad(){
        viewModelScope.launch {
            state.emit(VoteState.Loading)
            listForVote.clear()
            pets.value = null
            getRatingFirst()
        }
    }

    fun next(){
        viewModelScope.launch {
            listForVote.removeAt(0)
            if(listForVote.isEmpty()) state.emit(VoteState.ErrorNoPet)
            else {
                if(listForVote.isNotEmpty() && listForVote.first().cardType == 2){
                    state.emit(VoteState.VoteBonus)
                    pets.emit(listForVote.first())
                }else{
                    pets.emit(listForVote.first())
                    state.emit(VoteState.VoteDefault)
                }
            }
        }
    }

    fun resetList(){
//        pets.value = null
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val votePetsUseCase: IGetVotePetsUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == VoteViewModel::class.java)
            return VoteViewModel(
                votePetsUseCase = votePetsUseCase) as T
        }

    }
}