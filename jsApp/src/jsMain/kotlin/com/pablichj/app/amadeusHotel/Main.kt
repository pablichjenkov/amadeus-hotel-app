package com.pablichj.app.amadeusHotel

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.platform.JsBridge
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val jsBridge = JsBridge()
        CanvasBasedWindow("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = AppBuilder.buildGraph(),
                    jsBridge = jsBridge,
                    onBackPress = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

