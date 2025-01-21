package ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui

import android.bluetooth.BluetoothProfile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.components.ConnectingDialog
import ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.components.DisconnectedDialog
import ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.components.InfoScreen
import ir.mneckoee.rsa.eval.feature.connect.view.implementation.viewmodel.ConnectScreenViewModel

@Composable
fun ConnectScreen(
    viewModel: ConnectScreenViewModel = hiltViewModel(),
    address: String,
) {

    LaunchedEffect(viewModel, address) {
        viewModel.connect(address)
    }

    val state by viewModel.connectionState.collectAsState()

    when (state.state) {
        BluetoothProfile.STATE_CONNECTING -> ConnectingDialog()
        BluetoothProfile.STATE_CONNECTED -> InfoScreen(state)
        else -> DisconnectedDialog()
    }

}