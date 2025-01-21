package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.NetworkWifi1Bar
import androidx.compose.material.icons.filled.NetworkWifi2Bar
import androidx.compose.material.icons.filled.NetworkWifi3Bar
import androidx.compose.material.icons.filled.SignalWifi0Bar
import androidx.compose.material.icons.filled.SignalWifi4Bar
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun DeviceItem(
    name: String,
    mac: String,
    rssi: Int?,
    onClick: () -> Unit
) {
    val signalLevel = remember(rssi) { calculateSignalLevel(rssi) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                clip = true,
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color(0xffF3F4F6), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = name, color = Color(0xff111827), style = MaterialTheme.typography.bodyLarge)
            Text(text = mac, color = Color(0xff6B7280), style = MaterialTheme.typography.bodyMedium)
        }

        Row(
            modifier = Modifier.align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (signalLevel) {
                    0 -> Icons.Default.SignalWifi0Bar
                    1 -> Icons.Default.NetworkWifi1Bar
                    2 -> Icons.Default.NetworkWifi2Bar
                    3 -> Icons.Default.NetworkWifi3Bar
                    4 -> Icons.Default.SignalWifi4Bar
                    else -> Icons.Default.SignalWifiOff
                },
                contentDescription = "Bluetooth Signal Strength",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = getSignalStrengthText(rssi),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xff6B7280)
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color(0xff9CA3AF)
            )
        }
    }
}

private fun calculateSignalLevel(rssi: Int?): Int {
    if (rssi == null) return 0 // No signal

    // Normalize RSSI to a range of 0-100 for easier calculation
    val normalizedRssi = if (rssi < -100) -100 else if (rssi > -30) -30 else rssi

    // Map RSSI to signal levels (adjust these ranges as needed)
    return when {
        abs(normalizedRssi) > 90 -> 0 // Very weak or no signal
        abs(normalizedRssi) > 80 -> 1 // Weak
        abs(normalizedRssi) > 70 -> 2 // Fair
        abs(normalizedRssi) > 60 -> 3 // Good
        else -> 4 // Excellent
    }
}

private fun getSignalStrengthText(rssi: Int?): String {
    return if (rssi != null) {
        "$rssi dBm"
    } else {
        "No Signal"
    }
}