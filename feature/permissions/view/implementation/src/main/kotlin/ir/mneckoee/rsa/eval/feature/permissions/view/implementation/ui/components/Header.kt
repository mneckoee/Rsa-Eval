package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.mneckoee.rsa.eval.core.icons.api.R

@Composable
fun Header() {
    Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
    Box(
        modifier = Modifier
            .background(color = Color(0xffEFF6FF), shape = CircleShape)
            .padding(28.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_gear),
            contentDescription = "",
            tint = Color(0xff3B82F6)
        )
    }
    Spacer(Modifier.height(24.dp))
    Text(
        text = "Welcome to the RSA BLE App",
        color = Color.Black,
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = "Please grant the following permissions to ensure the app works properly",
        color = Color(0xff4B5563),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(16.dp))
}