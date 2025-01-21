package ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.mneckoee.rsa.eval.core.icons.api.R

@Composable
fun ConnectingDialog() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff5f5f5))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(36.dp)
                .widthIn(max = 320.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = true,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.background(color = Color(0x1A007AFF), shape = CircleShape)) {
                Icon(
                    painter = painterResource(R.drawable.state_connecting),
                    contentDescription = "",
                    tint = Color(0xff007AFF)
                )
            }
            Spacer(Modifier.size(24.dp))
            Text(
                text = "Connecting...",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xff1C1C1E)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Please wait while we establish connection",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xff8E8E93),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(28.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        //todo
                    }
                    .padding(8.dp),
                text = "Cancel",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xff007AFF)
            )
        }
    }
}