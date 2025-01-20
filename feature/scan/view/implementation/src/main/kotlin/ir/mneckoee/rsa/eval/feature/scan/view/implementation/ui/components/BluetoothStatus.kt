package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BluetoothStatus(bluetoothEnabled: Boolean) {
    Row(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xffF9FAFB)
            )
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Color(0xff4B5563)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Bluetooth:",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (bluetoothEnabled) "On" else "Off",
            style = MaterialTheme.typography.labelLarge,
            color = if (bluetoothEnabled) Color(0xff16A34A) else Color.Red
        )
    }
}