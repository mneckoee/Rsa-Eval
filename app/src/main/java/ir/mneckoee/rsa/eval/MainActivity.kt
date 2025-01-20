package ir.mneckoee.rsa.eval

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
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
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent),
                    navController = navController, startDestination = "scan") {
                    composable("scan") { ScanScreen() }
                }
            }
        }
    }
}
