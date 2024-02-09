plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("io.github.pablichjenkov:component-toolkit:0.5.17")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
                implementation("androidx.activity:activity-compose:1.8.1")
            }
        }
    }
}

android {
    namespace = "com.pablichj.app.amadeusHotel"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.pablichj.app.amadeusHotel"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packagingOptions {
        resources {
            // excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts.apply {
                add("META-INF/kotlinx_coroutines_core.version")
                add("META-INF/INDEX.LIST")
                add("META-INF/versions/9/previous-compilation-data.bin")
            }
        }
    }
}