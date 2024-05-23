// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.googleService) apply false
}
allprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }
    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
            debug = true
        }
    }
    ktlint {
        debug = true
        verbose = true
        android = true
        outputToConsole = true
        outputColorName = "RED"
        enableExperimentalRules = false
    }
}
