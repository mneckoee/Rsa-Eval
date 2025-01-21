android {
    namespace = "ir.mneckoee.rsa.eval.feature.connect.domain.implementation"
}

dependencies {
    implementation(project(":feature:connect:domain:api"))
    implementation(libs.bundles.autodagger)
    implementation(libs.hilt)
    ksp(libs.autodagger.ksp)
}