package ir.mneckoee.rsa.eval.feature.connect.view.implementation.viewmodel

import android.bluetooth.BluetoothProfile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mneckoee.rsa.eval.feature.connect.domain.api.model.ConnectionState
import ir.mneckoee.rsa.eval.feature.connect.domain.api.usecase.ConnectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectScreenViewModel @Inject constructor(
    private val connectUseCase: ConnectUseCase
) : ViewModel() {

    private val connectionStateInternal = MutableStateFlow(
        ConnectionState(
            state = BluetoothProfile.STATE_CONNECTING,
            services = emptyList()
        )
    )
    val connectionState = connectionStateInternal.asStateFlow()

    fun connect(address: String) = viewModelScope.launch {
        connectUseCase.connect(address).onEach {
            connectionStateInternal.emit(it)
        }.launchIn(viewModelScope)
    }
}