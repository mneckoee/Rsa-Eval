package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun PermissionRow(
    modifier: Modifier,
    granted: Boolean,
    denied: Boolean,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(enabled = !granted) {
            onCheckedChange(!checked)
        }
        .padding(top = 12.dp, bottom = 16.dp), verticalAlignment = Alignment.Top) {
        Checkbox(enabled = !granted, checked = checked || granted, onCheckedChange = onCheckedChange)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 12.dp, end = 16.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = title,
                    color = Color(0xff111827),
                    style = MaterialTheme.typography.bodyLarge
                )
                if (granted) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = " (granted)",
                        color = Color(0xff16A34A),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else if (denied) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = " (denied)",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text(
                text = description,
                color = Color(0xff4B5563),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}