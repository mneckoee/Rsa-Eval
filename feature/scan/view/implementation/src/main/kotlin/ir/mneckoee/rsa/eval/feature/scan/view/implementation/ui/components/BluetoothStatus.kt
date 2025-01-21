package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.mneckoee.rsa.eval.core.icons.api.R

@Composable
fun BluetoothStatus(bluetoothEnabled: Boolean) {
    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = Color(0xffF9FAFB)
            )
            .fillMaxWidth()
            .clickable(enabled = !bluetoothEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBtIntent)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bt),
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