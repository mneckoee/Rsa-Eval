package ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.mneckoee.rsa.eval.core.icons.api.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun ScanButton(
    isScanning: Boolean,
    onClick: () -> Unit
) {
    var date by rememberSaveable { mutableLongStateOf(0L) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(ir.mneckoee.rsa.eval.feature.scan.view.implementation.R.raw.scan_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f,
        restartOnPlay = false
    )

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .alpha(if (isScanning) 0f else 1f)
                .fillMaxWidth()
                .clickable(enabled = !isScanning) {
                    date = System.currentTimeMillis()
                    onClick()
                }
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF2E5CFF), Color(0xFF507AFF)),
                        ),
                        shape = CircleShape
                    )
                    .requiredSize(64.dp)
                    .padding(16.dp),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "",
                tint = Color.White
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Start Scan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xff111827)
            )
            Spacer(Modifier.size(8.dp))
            val lastTime = getLastTime(date)
            Text(
                text = if (date == 0L) "No scan yet" else lastTime,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xff6B7280)
            )
            Spacer(Modifier.size(16.dp))
        }
        LottieAnimation(
            modifier = Modifier.fillMaxSize().alpha(if (isScanning) 1f else 0f),
            composition = composition,
            progress = { progress },
        )
    }
}

private fun getLastTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val weeks = days / 7

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 2 -> "Yesterday"
        days < 7 -> "$days days ago"
        weeks < 4 -> "$weeks weeks ago" // Up to roughly a month
        else -> {
            // More than a month ago, show date
            val date = Date(timestamp)
            val format =
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) // Customize date format
            format.format(date)
        }
    }
}