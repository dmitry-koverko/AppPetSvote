package com.petsvote.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.params.AddVoteParams
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.domain.usecases.rating.impl.AddVoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddVoteViewModel @Inject constructor(
    private val addVoteUseCase: IAddVoteUseCase
    ) : BaseViewModel() {

    fun addVote(addVoteParams: AddVoteParams){
        viewModelScope.launch {
            addVoteUseCase.addVote(addVoteParams)
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val addVoteUseCase: IAddVoteUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AddVoteViewModel::class.java)
            return AddVoteViewModel(addVoteUseCase = addVoteUseCase) as T
        }

    }

}