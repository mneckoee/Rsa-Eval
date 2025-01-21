package ir.mneckoee.rsa.eval.feature.scan.domain.api.model

import android.bluetooth.BluetoothDevice

data class ScanBleResult(
    val device: BluetoothDevice?,
    val rssi: Int?,
    val failed: Boolean = false,
)