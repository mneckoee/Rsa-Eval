package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import ir.mneckoee.rsa.eval.core.icons.api.R
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.components.Header
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.components.permissionsSection
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.bluetoothPermissions
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry.locationPermissions
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.viewmodel.PermissionsViewModel


@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = hiltViewModel(),
    onPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val allPermissionGranted by viewModel.allPermissionGranted.collectAsState()
    val showUI by viewModel.showUi.collectAsState(false)
    var requestPermission by remember { mutableStateOf("") }

    LaunchedEffect(allPermissionGranted) {
        if (allPermissionGranted)
            onPermissionsGranted()
    }
    LifecycleResumeEffect(viewModel) {
        viewModel.checkPermissions()
        onPauseOrDispose { }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val permission = result.keys.first()
        val granted = result[permission]
        if (granted == true) {
            val nextPermission =
                (bluetoothPermissions + locationPermissions).map { it.manifestName }
                    .first { context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
            requestPermission = nextPermission
        } else {
            Toast.makeText(context, "Go to Settings and Grant All Permissions", Toast.LENGTH_LONG).show()
        }

    }

    if (!showUI) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
        }
        return
    }

    val permissionChecked by viewModel.permissionChecked.collectAsState()
    val proceedButtonEnabled by viewModel.proceedButtonEnable.collectAsState(false)
    val lazyState = rememberLazyListState()

    LaunchedEffect(proceedButtonEnabled) {
        if (proceedButtonEnabled)
            lazyState.animateScrollToItem(100) // scroll to bottom
    }

    LaunchedEffect(requestPermission) {
        if (requestPermission.isNotEmpty()) {
            permissionLauncher.launch(arrayOf(requestPermission))
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 24.dp),
        state = lazyState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Header()
        }
        item {
            Spacer(Modifier.height(32.dp))
        }
        permissionsSection(
            headerIcon = R.drawable.bluetooth_permission,
            headerTitle = "Bluetooth Permissions",
            appPermissions = bluetoothPermissions,
            permissionChecked = { manifestName ->
                permissionChecked[manifestName] ?: false
            },
            onCheckChanged = { manifestName, state ->
                viewModel.onCheckChanged(manifestName, state)
            }
        )
        item {
            Spacer(Modifier.height(24.dp))
        }
        permissionsSection(
            headerIcon = R.drawable.location_permission,
            headerTitle = "Location Permissions",
            appPermissions = locationPermissions,
            permissionChecked = { manifestName ->
                permissionChecked[manifestName] ?: false
            },
            onCheckChanged = { manifestName, state ->
                viewModel.onCheckChanged(manifestName, state)
            }
        )
        item {
            Spacer(Modifier.height(32.dp))
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = proceedButtonEnabled, onClick = {
                    permissionLauncher.launch(
                        (bluetoothPermissions + locationPermissions).map { it.manifestName }
                            .filter { context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
                            .take(1)
                            .toTypedArray()

                    )
                }) {
                Text(text = "Grant Permissions", style = MaterialTheme.typography.bodyLarge)
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
        item {
            Text(modifier = Modifier.clickable {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.setData(uri)
                context.startActivity(intent)
            }, text = "Open App Settings", textDecoration = TextDecoration.Underline)
        }
        item { Spacer(Modifier.height(12.dp)) }
        item { Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars)) }
    }
}