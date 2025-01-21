package ir.mneckoee.rsa.eval.feature.connect.domain.api.model

import android.bluetooth.BluetoothGattService

data class ConnectionState(
    val state: Int,
    val services: List<BluetoothGattService>
)