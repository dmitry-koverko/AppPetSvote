package com.petsvote.domain.repository

import com.petsvote.domain.entity.configuration.Document

interface IConfigurationRepository {

    suspend fun getUserAgreement(): Document
    suspend fun getPrivacyPolicy(): Document

}