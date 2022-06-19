package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.ISetImageCropUseCase
import com.petsvote.domain.usecases.configuration.ISetImageUseCase
import javax.inject.Inject

class SetImageCropUseCase @Inject constructor(
    private val userRepository: IUserRepository
): ISetImageCropUseCase {
    override suspend fun setImageCrop(bytes: ByteArray) {
        userRepository.setImageCrop(bytes)
    }
}