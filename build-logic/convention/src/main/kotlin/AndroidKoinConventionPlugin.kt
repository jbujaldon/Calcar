import com.calcar.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                val bom = libs.findLibrary("koin-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("koin-android").get())
                add("implementation", libs.findLibrary("koin-compose").get())
            }
        }
    }
}