package com.pablichj.app.amadeusHotel

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.AppLifecycleEvent
import com.macaosoftware.platform.DefaultAppLifecycleDispatcher
import com.macaosoftware.platform.DesktopBridge

fun main() {
    //todo: Use the adaptable node in this case
    val windowState = WindowState(size = DpSize(500.dp, 800.dp))
    val rootComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        componentViewModel = BottomNavigationDemoViewModel(),
        content = NavBarComponentDefaults.NavBarComponentView
    )
    val desktopBridge = DesktopBridge()
    singleWindowApplication(
        title = "Hotel Booking",
        state = windowState
    ) {
        DesktopComponentRender(
            rootComponent = rootComponent,
            windowState = windowState,
            desktopBridge = desktopBridge
        )
    }
}
