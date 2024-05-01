plugins {
    id("calcar.android.library")
    id("calcar.android.koin")
}

android {
    namespace = "com.calcar.dependencies"
}

dependencies {
    implementation(project(":common:domain"))
    implementation(project(":common:ui"))
}