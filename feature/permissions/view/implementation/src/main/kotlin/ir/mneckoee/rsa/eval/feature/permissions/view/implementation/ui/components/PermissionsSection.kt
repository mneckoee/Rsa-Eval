package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.AppPermission
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.bluetoothPermissions

@OptIn(ExperimentalPermissionsApi::class)
fun LazyListScope.permissionsSection(
    headerIcon: Int,
    headerTitle: String,
    appPermissions: List<AppPermission>,
    permissionChecked: (String) -> Boolean,
    onCheckChanged: (String, Boolean) -> Unit
) {
    item {
        SectionHeader(icon = headerIcon, title = headerTitle)
    }
    itemsIndexed(appPermissions) { index, permission ->
        val permissionState = rememberPermissionState(
            permission.manifestName
        )
        val granted = PermissionStatus.Granted == permissionState.status
        PermissionRow(
            modifier = Modifier.background(
                color =
                Color(0xffF9FAFB),
                shape = when (index) {
                    bluetoothPermissions.lastIndex -> RoundedCornerShape(
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )

                    else -> RectangleShape
                }
            ),
            granted = granted,
            denied = false, // cannot determine that permission is denied once
            title = permission.displayName,
            description = permission.description,
            checked = permissionChecked(permission.manifestName),
            onCheckedChange = { onCheckChanged(permission.manifestName, it) }
        )
        if (index != bluetoothPermissions.lastIndex) {
            HorizontalDivider()
        }
    }
}