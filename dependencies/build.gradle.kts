plugins {
    id("calcar.android.library")
    id("calcar.android.koin")
}

android {
    namespace = "com.calcar.dependencies"
}

dependencies {
    implementation(project(":common:data"))
    implementation(project(":common:database"))
    implementation(project(":common:datastore"))
    implementation(project(":common:domain"))
    implementation(project(":common:ui"))
}