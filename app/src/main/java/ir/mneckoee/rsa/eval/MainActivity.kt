package ir.mneckoee.rsa.eval

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.mneckoee.rsa.eval.feature.permissions.view.implementation.ui.PermissionsScreen
import ir.mneckoee.rsa.eval.feature.scan.view.implementation.ui.ScanScreen
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
                    navController = navController, startDestination = "permissions") {
                    composable("scan") { ScanScreen() }
                    composable("permissions") { PermissionsScreen(
                        onPermissionsGranted = {
                            navController.navigate("scan") {
                                popUpTo("permissions") { inclusive = true } // Remove permissions from backstack
                            }
                        }
                    ) }
                }
            }
        }
    }
}
