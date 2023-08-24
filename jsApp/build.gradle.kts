plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(project(":shared"))
            //implementation(compose.html.core)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

