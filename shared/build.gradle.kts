plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("native.cocoapods")
    id("com.github.gmazzo.buildconfig")
}

group = "com.pablichj"
version = "0.1.0"

kotlin {

    // IOS
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Shared code for the Hotel Booking template"
        homepage = "https://github.com/pablichjenkov/templato"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "HotelBookingKt"
            isStatic = true
        }
    }

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm("desktop")

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation("io.github.pablichjenkov:component-toolkit:0.5.10-rc01")
                implementation("io.github.pablichjenkov:amadeus-api:0.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // IOS
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        // JS
        val jsMain by getting

        // JVM
        val desktopMain by getting
    }

}

buildConfig {
    useKotlinOutput { internalVisibility = true }
    packageName("com.pablichj.app.amadeusHotel.shared")

    val amadeusApiKey = extra["amadeus.apiKey"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api key from amadeus and place it in local.properties as `amadeus.apiKey`"
    }

    val amadeusApiSecret = extra["amadeus.apiSecret"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api secret from amadeus and place it in local.properties as `amadeus.apiSecret`"
    }

    buildConfigField(
        "String",
        "AMADEUS_API_KEY", amadeusApiKey
    )

    buildConfigField(
        "String",
        "AMADEUS_API_SECRET", amadeusApiSecret
    )

}
