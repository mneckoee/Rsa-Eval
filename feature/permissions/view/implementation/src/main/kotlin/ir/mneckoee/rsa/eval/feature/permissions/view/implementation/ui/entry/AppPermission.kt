package ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.entry

import android.Manifest
import android.os.Build

data class AppPermission(
    val manifestName: String,
    val displayName: String,
    val description: String,
)

val bluetoothPermissions =
    mutableListOf<AppPermission>().apply {
        if (Build.VERSION.SDK_INT <= 30) {
            add(
                AppPermission(
                    manifestName = Manifest.permission.BLUETOOTH,
                    displayName = "Bluetooth",
                    description = "Required to discover and connect to nearby devices"
                ),
            )
            add(
                AppPermission(
                    manifestName = Manifest.permission.BLUETOOTH_ADMIN,
                    displayName = "Bluetooth Admin",
                    description = "Required for device discovery and pairing"
                )
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            add(
                AppPermission(
                    manifestName = Manifest.permission.BLUETOOTH_CONNECT,
                    displayName = "Bluetooth Connect",
                    description = "Allows the app to connect with paired devices"
                ),
            )
            add(
                AppPermission(
                    manifestName = Manifest.permission.BLUETOOTH_SCAN,
                    displayName = "Bluetooth Scan",
                    description = "This will allow the app to detect and connect to compatible Bluetooth devices."
                )
            )
        }

    }.toList()

val locationPermissions =
    mutableListOf<AppPermission>().apply {
        add(
            AppPermission(
                manifestName = Manifest.permission.ACCESS_COARSE_LOCATION,
                displayName = "Coarse Location Access",
                description = "Required for finding nearby devices and services"
            ),
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            add(
                AppPermission(
                    manifestName = Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    displayName = "Background Location",
                    description = "Allows the app to access location while running in background"
                ),
            )
        }
        add(
            AppPermission(
                manifestName = Manifest.permission.ACCESS_FINE_LOCATION,
                displayName = "Fine Location Access",
                description = "Required for finding nearby devices and services"
            ),
        )
    }.toList()
