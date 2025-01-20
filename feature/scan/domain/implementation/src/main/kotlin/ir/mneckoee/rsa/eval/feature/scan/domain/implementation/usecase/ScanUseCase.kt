package ir.mneckoee.rsa.eval.feature.scan.domain.implementation.usecase

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
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
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
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
                        val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
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

}