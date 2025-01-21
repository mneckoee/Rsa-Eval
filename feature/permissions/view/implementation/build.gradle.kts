plugins {
    alias(libs.plugins.kotlin.compose)
}
android {
    namespace = "ir.mneckoee.rsa.eval.feature.permissions.view.implementation"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    implementation(libs.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(project(":core:icons:api"))
}