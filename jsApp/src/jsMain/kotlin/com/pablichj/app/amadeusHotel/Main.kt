package com.pablichj.app.amadeusHotel

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.JsBridge
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val jsBridge = JsBridge()
        val bottomNavigationComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = BottomNavigationDemoViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        )
        CanvasBasedWindow("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = bottomNavigationComponent,
                    jsBridge = jsBridge,
                    onBackPress = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

