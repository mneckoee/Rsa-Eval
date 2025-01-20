android {
    namespace = "ir.mneckoee.rsa.eval.feature.scan.domain.implementation"
}

dependencies {
    implementation(project(":feature:scan:domain:api"))
    implementation(libs.bundles.autodagger)
    implementation(libs.hilt)
    ksp(libs.autodagger.ksp)
}