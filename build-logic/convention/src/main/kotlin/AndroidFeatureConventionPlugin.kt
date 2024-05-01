import com.android.build.api.dsl.LibraryExtension
import com.calcar.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("calcar.android.library")
                apply("calcar.android.koin")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                add("implementation", project(":common:domain"))
                add("implementation", project(":common:ui"))

                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
            }
        }
    }
}