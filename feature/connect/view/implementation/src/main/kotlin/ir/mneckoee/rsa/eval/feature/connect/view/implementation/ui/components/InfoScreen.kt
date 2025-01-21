package ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.mneckoee.rsa.eval.feature.connect.domain.api.model.ConnectionState
import java.util.UUID

@Composable
fun InfoScreen(
    state: ConnectionState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item { Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars)) }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color(0xff007AFF)
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Device Info",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.clickable { },
                    text = "Disconnect",
                    textAlign = TextAlign.Center,
                    color = Color(0xff007AFF),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        items(state.services) { service ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color(0xffF9FAFB), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    standardServiceNames[service.uuid] ?: "Custom",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xff4B5563)
                )
                Text(
                    "UUID: " + service.uuid.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xff6B7280)
                )
            }
        }
    }
}

private val standardServiceNames = mapOf(
    UUID.fromString("00001800-0000-1000-8000-00805f9b34fb") to "Generic Access",
    UUID.fromString("00001801-0000-1000-8000-00805f9b34fb") to "Generic Attribute",
    UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb") to "Device Information",
    UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb") to "Heart Rate",
)