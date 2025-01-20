package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.mneckoee.rsa.eval.core.icons.api.R

@Composable
fun ScanPageInfo() {
    Row(
        modifier = Modifier
            .background(
                color = Color(0xffF5F8FF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_info),
            contentDescription = "",
            tint = Color(0xff007AFF)
        )
        Spacer(Modifier.size(4.dp))
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(
                text = "How it works",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(Modifier.size(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "This app scans for nearby Bluetooth Low " +
                        "Energy (BLE) devices. Ensure Bluetooth is " +
                        "enabled on your device.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff4B5563)
            )
        }
    }
}