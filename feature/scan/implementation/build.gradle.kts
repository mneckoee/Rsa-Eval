plugins {
    alias(libs.plugins.kotlin.compose)
}
android {
    namespace = "ir.mneckoee.rsa.eval.feature.scan.implementation"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}