package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components.BluetoothStatus
import ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components.ScanPageInfo
import ir.mneckoee.rsa.eval.feature.scan.view.implementation.viewmodel.ScanScreenViewModel

@Composable
fun ScanScreen(
    viewModel: ScanScreenViewModel = hiltViewModel()
) {
    val isBluetoothOn by viewModel.isBluetoothOn.collectAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.height(61.dp),
            text = "Rsa BLE Scanner",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(modifier = Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth + 32.dp.toPx().toInt()
                )
            )
            layout(placeable.width, placeable.height) {
                placeable.place(0, 0, 0f)
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        ScanPageInfo()
        Spacer(modifier = Modifier.height(16.dp))
        BluetoothStatus(bluetoothEnabled = isBluetoothOn)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        ScanButton(isScanning = state.isScanning, onClick = viewModel::onScanButtonClicked)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        FoundDevicesList(devices = state.foundDevices)
    }
}