package com.petsvote.domain.usecases.rating

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.VotePet
import kotlinx.coroutines.flow.Flow

interface IGetVotePetsUseCase {
    suspend fun getRating(): Flow<List<VotePet>>
}