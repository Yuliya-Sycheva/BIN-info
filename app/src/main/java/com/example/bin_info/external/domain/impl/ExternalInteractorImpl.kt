package com.example.bin_info.external.domain.impl

import com.example.bin_info.external.domain.ExternalInteractor
import com.example.bin_info.external.domain.ExternalRepository

class ExternalInteractorImpl(private val externalRepository: ExternalRepository) :
    ExternalInteractor {
    override fun openMap(latitude: String, longitude: String) {
        externalRepository.openMap(latitude, longitude)
    }

    override fun openPhone(phone: String) {
        externalRepository.openPhone(phone)
    }

    override fun openUrl(url: String) {
        externalRepository.openUrl(url)
    }
}