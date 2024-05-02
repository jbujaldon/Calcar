plugins {
    id("calcar.android.library")
    id("calcar.android.koin")
}

android {
    namespace = "com.calcar.common.data"
}

dependencies {
    implementation(project(":common:database"))
    implementation(project(":common:domain"))
}