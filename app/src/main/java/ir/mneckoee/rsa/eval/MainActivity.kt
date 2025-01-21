package ir.mneckoee.rsa.eval

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.mneckoee.rsa.eval.feature.connect.view.implementation.ui.ConnectScreen
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.PermissionsScreen
import ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.ScanScreen
import ir.mneckoee.rsa.eval.permission.PermissionChecker
import ir.mneckoee.rsa.eval.ui.theme.RsaEvalTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RsaEvalTaskTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = Routes.SCAN
                ) {
                    composable(Routes.SCAN) {
                        ScanScreen(onDeviceSelected = { address ->
                            if (address.isNotEmpty())
                                navController.navigate(Routes.buildConnectRoute(address))
                        })
                    }
                    composable(Routes.PERMISSIONS) {
                        PermissionsScreen(
                            onPermissionsGranted = {
                                navController.navigateUp()
                            },
                            onBackPressed = {
                                finish()
                            }
                        )
                    }
                    composable(Routes.CONNECT) { backStackEntry ->
                        val address =
                            backStackEntry.arguments?.getString(Routes.ConnectArgs.ADDRESS) ?: ""
                        ConnectScreen(address = address)
                    }
                }
                PermissionChecker {
                    if (navController.currentBackStackEntry?.destination?.route != "permissions")
                        navController.navigate("permissions")
                }
            }
        }
    }
}

private object Routes {
    const val SCAN = "scan"
    const val PERMISSIONS = "permissions"
    const val CONNECT = "connect/{${ConnectArgs.ADDRESS}}"

    fun buildConnectRoute(address: String) = "connect/$address"

    object ConnectArgs {
        const val ADDRESS = "address"
    }
}
