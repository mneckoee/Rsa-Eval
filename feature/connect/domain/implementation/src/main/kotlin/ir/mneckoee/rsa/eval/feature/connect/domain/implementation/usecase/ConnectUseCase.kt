package ir.mneckoee.rsa.eval.feature.connect.domain.implementation.usecase

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mneckoee.rsa.eval.feature.connect.domain.api.model.ConnectionState
import ir.mneckoee.rsa.eval.feature.connect.domain.api.usecase.ConnectUseCase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import se.ansman.dagger.auto.AutoBind
import javax.inject.Inject

@AutoBind
class ConnectUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ConnectUseCase {

    override fun connect(address: String): Flow<ConnectionState> {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val bluetoothAdapter = bluetoothManager?.adapter ?: return flowOf()

        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(address)

        return callbackFlow {
            var bluetoothGatt: BluetoothGatt? = null
            val gattCallback = object : BluetoothGattCallback() {
                override fun onConnectionStateChange(
                    gatt: BluetoothGatt?,
                    status: Int,
                    newState: Int
                ) {
                    super.onConnectionStateChange(gatt, status, newState)
                    when (newState) {
                        BluetoothProfile.STATE_CONNECTED -> {
                            gatt?.discoverServices() // Discover services immediately after connection
                        }

                        BluetoothProfile.STATE_DISCONNECTED -> {
                            bluetoothGatt?.close()
                            bluetoothGatt = null
                        }

                        BluetoothProfile.STATE_CONNECTING -> {

                        }

                        BluetoothProfile.STATE_DISCONNECTING -> {

                        }
                    }
                    trySend(ConnectionState(newState, emptyList()))
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                    super.onServicesDiscovered(gatt, status)
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        trySend(
                            ConnectionState(
                                state = BluetoothProfile.STATE_CONNECTED,
                                services = gatt?.services?: emptyList()
                            )
                        )

                    } else {
                        trySend(
                            ConnectionState(
                                state = BluetoothProfile.STATE_DISCONNECTED,
                                services = emptyList()
                            )
                        )
                    }
                }

                override fun onCharacteristicChanged(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    value: ByteArray
                ) {
                    super.onCharacteristicChanged(gatt, characteristic, value)
                }

                override fun onDescriptorWrite(
                    gatt: BluetoothGatt?,
                    descriptor: BluetoothGattDescriptor?,
                    status: Int
                ) {
                    super.onDescriptorWrite(gatt, descriptor, status)
                }
            }
            bluetoothGatt = bluetoothDevice.connectGatt(context, true, gattCallback)

            awaitClose {

            }
        }
    }


}