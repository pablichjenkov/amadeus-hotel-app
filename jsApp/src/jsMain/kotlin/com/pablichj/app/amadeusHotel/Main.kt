package com.pablichj.app.amadeusHotel

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {

        val bottomNavigationComponent = BottomNavigationComponent(
            viewModelFactory = RootBottomNavigationDemoViewModelFactory(
                bottomNavigationStatePresenter = BottomNavigationComponentDefaults.createBottomNavigationStatePresenter(),
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        )
        CanvasBasedWindow("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = bottomNavigationComponent,
                    onBackPress = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

