package com.pablichj.app.amadeusHotel

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.platform.DesktopBridge

fun main() {
    //todo: Use the adaptable node in this case
    val windowState = WindowState(size = DpSize(500.dp, 800.dp))
    val desktopBridge = DesktopBridge()

    val rootComponent = BottomNavigationComponent(
        viewModelFactory = RootBottomNavigationDemoViewModelFactory(
            bottomNavigationStatePresenter = BottomNavigationComponentDefaults.createBottomNavigationStatePresenter(),
        ),
        content = BottomNavigationComponentDefaults.BottomNavigationComponentView
    )

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
