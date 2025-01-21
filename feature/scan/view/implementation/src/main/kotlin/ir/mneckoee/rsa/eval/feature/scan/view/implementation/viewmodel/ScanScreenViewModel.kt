package ir.mneckoee.rsa.eval.feature.scan.view.implementation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mneckoee.rsa.eval.feature.scan.domain.api.model.ScanBleResult
import ir.mneckoee.rsa.eval.feature.scan.domain.api.usecase.ScanUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val scanUseCase: ScanUseCase
) : ViewModel() {

    val isBluetoothOn = scanUseCase.isBluetoothOn()
    private var scanJob: Job? = null

    private val isScanningInternal = MutableStateFlow(false)
    val isScanning = isScanningInternal.asStateFlow()

    private val scannedDevicesInternal = MutableStateFlow<List<ScanBleResult>>(emptyList())
    val scannedDevices = scannedDevicesInternal.asStateFlow()

    fun startScan() = viewModelScope.launch {
        scanJob?.cancel()
        isScanningInternal.emit(true)
        scannedDevicesInternal.emit(emptyList())
        scanUseCase.scanDevice().onEach { result ->
            val currentDevices = scannedDevicesInternal.value.toMutableList()
            result.device?.let { device ->
                if (currentDevices.find { result.device?.address == device.address } == null)
                    currentDevices.add(result)
                else {
                    val index =
                        currentDevices.indexOfFirst { result.device?.address == device.address }
                    if (index > 0) {
                        currentDevices[index] = result
                    } else {
                    }
                }
            }
            scannedDevicesInternal.emit(currentDevices.toSet().toList())
            if (result.failed) {
                scanJob?.cancel()
                isScanningInternal.emit(false)
            }
        }.launchIn(viewModelScope).apply {
            scanJob = this
        }
    }

    fun stopScan() {
        scanJob?.cancel()
        isScanningInternal.tryEmit(false)
    }
}