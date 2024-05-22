plugins {
    id("calcar.android.feature")
    id("calcar.android.library.compose")
}

android {
    namespace = "com.calcar.feature.onboarding"
}

dependencies {
    implementation(project(":feature:vehicles"))
}