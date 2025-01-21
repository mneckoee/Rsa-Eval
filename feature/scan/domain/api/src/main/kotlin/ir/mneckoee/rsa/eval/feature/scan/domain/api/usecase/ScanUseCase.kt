package ir.mneckoee.rsa.eval.feature.scan.domain.api.usecase

import ir.mneckoee.rsa.eval.feature.scan.domain.api.model.ScanBleResult
import kotlinx.coroutines.flow.Flow

interface ScanUseCase {
    fun isBluetoothOn(): Flow<Boolean>

    fun scanDevice(): Flow<ScanBleResult>
}