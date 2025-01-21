package ir.mneckoee.rsa.eval.feature.connect.domain.api.usecase

import ir.mneckoee.rsa.eval.feature.connect.domain.api.model.ConnectionState
import kotlinx.coroutines.flow.Flow

interface ConnectUseCase {
    fun connect(address: String) : Flow<ConnectionState>
}