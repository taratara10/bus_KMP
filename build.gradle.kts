import io.gitlab.arturbosch.detekt.Detekt

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.androidGradleLibraryPlugin) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}

dependencies {
    detektPlugins(project(":detekt"))
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
//    config.setFrom(file("$rootDir/detekt/src/main/resources/config/config.yml"))
    source.setFrom(
        "$rootDir/app-wearos",
        "$rootDir/app-wasm",
        "$rootDir/feature",
        "$rootDir/core",
    )
}
tasks.withType<Detekt> { dependsOn(":detekt:assemble") }

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
