// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
//    dependencies {
////        classpath(libs.plugins.navigation.safe.args)
//    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    id ("com.android.library") version "7.3.1" apply false
}