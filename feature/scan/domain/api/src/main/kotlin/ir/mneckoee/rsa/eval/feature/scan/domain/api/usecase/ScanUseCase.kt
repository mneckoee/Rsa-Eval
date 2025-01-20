package ir.mneckoee.rsa.eval.feature.scan.domain.api.usecase

import kotlinx.coroutines.flow.Flow

interface ScanUseCase {
    fun isBluetoothOn(): Flow<Boolean>
}