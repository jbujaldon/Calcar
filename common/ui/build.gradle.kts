plugins {
    id("calcar.android.library")
    id("calcar.android.library.compose")
    id("calcar.android.koin")
}

android {
    namespace = "com.calcar.common.ui"
}

dependencies {
    implementation(project(":common:domain"))
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling.preview)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.navigation.compose)
}