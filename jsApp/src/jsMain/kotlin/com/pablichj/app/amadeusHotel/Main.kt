package com.pablichj.app.amadeusHotel

import androidx.compose.material.MaterialTheme
import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.BrowserViewportWindow
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        /*Window("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = HelloWorldComponent(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }*/
        BrowserViewportWindow("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = AppBuilder.buildGraph(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

