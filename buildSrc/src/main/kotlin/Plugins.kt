/**
 * @author lusinabrian on 29/06/20.
 * @Notes Plugins
 */
object Plugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0"

    const val jacocoPluginVersion = "0.8.5"
    const val jacocoPlugin = "org.jacoco:org.jacoco.core:$jacocoPluginVersion"

    const val detektPlugin = "io.gitlab.arturbosch.detekt"
    const val detektPluginVersion = "1.9.1"
    const val detektFormatter =
        "io.gitlab.arturbosch.detekt:detekt-formatting:$detektPluginVersion"
}
