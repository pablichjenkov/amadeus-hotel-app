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
                implementation("io.github.pablichjenkov:component-toolkit:0.5.17")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
            }
        }
    }
}

compose.experimental {
    web.application {}
}

