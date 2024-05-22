plugins {
    id("calcar.android.library")
    id("calcar.android.koin")
    id("com.google.protobuf")
}

android {
    namespace = "com.calcar.common.datastore"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") { option("lite") }
                register("kotlin") { option("lite") }
            }
        }
    }
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(libs.protobuf.kotlin.lite)
}