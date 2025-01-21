package ir.mneckoee.rsa.eval.feature.scan.domain.implementation.usecase

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mneckoee.rsa.eval.feature.scan.domain.api.model.ScanBleResult
import ir.mneckoee.rsa.eval.feature.scan.domain.api.usecase.ScanUseCase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import se.ansman.dagger.auto.AutoBind
import javax.inject.Inject

@AutoBind
class ScanUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ScanUseCase {

    override fun isBluetoothOn(): Flow<Boolean> {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val bluetoothAdapter = bluetoothManager?.adapter
            ?: // Device doesn't support Bluetooth
            return flowOf(false)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle permission issue. You should request the permission before calling this function.
            return flowOf(false)
        }

        return callbackFlow {
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val action = intent.action
                    if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                        val state =
                            intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                        trySend(state == BluetoothAdapter.STATE_ON)
                    }
                }
            }

            val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            context.registerReceiver(receiver, filter)
            trySend(bluetoothAdapter.isEnabled) // Emit initial state

            awaitClose {
                context.unregisterReceiver(receiver)
            }
        }
    }

    override fun scanDevice(): Flow<ScanBleResult> {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val bluetoothAdapter = bluetoothManager?.adapter
            ?: // Device doesn't support Bluetooth
            return flowOf()

        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        return callbackFlow {
            val leScanCallback: ScanCallback = object : ScanCallback() {
                override fun onScanResult(callbackType: Int, result: ScanResult) {
                    super.onScanResult(callbackType, result)
                    val device = result.device
                    val rssi = result.rssi
                    trySend(
                        ScanBleResult(
                            device,
                            rssi,
                            failed = false
                        )
                    )
                }

                override fun onScanFailed(errorCode: Int) {
                    super.onScanFailed(errorCode)
                    trySend(
                        ScanBleResult(
                            null,
                            null,
                            failed = true
                        )
                    )
                }
            }
            val settings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // Or other scan modes
                .build()

            val filter: List<ScanFilter> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                listOf(
//                ScanFilter.Builder()
//                    .setServiceUuid(ParcelUuid(UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb")))
//                    .build()
                )
            } else emptyList()

            try {
                bluetoothLeScanner.startScan(filter, settings, leScanCallback)
            } catch (e: SecurityException) {
                trySend(
                    ScanBleResult(
                        null,
                        null,
                        failed = true
                    )
                )
            }

            awaitClose {
                bluetoothLeScanner.stopScan(leScanCallback)
            }
        }
    }


}