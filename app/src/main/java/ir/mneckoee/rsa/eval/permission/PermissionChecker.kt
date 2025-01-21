package ir.mneckoee.rsa.eval.permission

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.bluetoothPermissions
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.locationPermissions

@Composable
fun PermissionChecker(
    onPermissionMissed: () -> Unit
) {
    val context = LocalContext.current
    LifecycleResumeEffect(Unit) {
        val allPermissions = bluetoothPermissions + locationPermissions
        val grantedStatus = allPermissions.map {
            it.manifestName
        }.map {
            context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }.all { it }
        if (!grantedStatus) {
            onPermissionMissed()
        }
        onPauseOrDispose { }
    }
}
