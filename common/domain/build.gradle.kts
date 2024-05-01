plugins {
    id("calcar.android.library")
    id("calcar.android.koin")
}

android {
    namespace = "com.calcar.common.domain"
}

dependencies {
    api(project(":common:core"))
}