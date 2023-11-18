package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.platform.DefaultAppLifecycleDispatcher
import com.macaosoftware.platform.IosBridge
import com.pablichj.app.amadeusHotel.RootBottomNavigationDemoViewModelFactory
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit
): UIViewController = ComposeUIViewController {
    IosComponentRender(
        rootComponent = rootComponent,
        iosBridge = iosBridge,
        onBackPress = onBackPress
    )
}

fun buildAppComponent(): Component {
    return BottomNavigationComponent(
        viewModelFactory = RootBottomNavigationDemoViewModelFactory(
            bottomNavigationStatePresenter = BottomNavigationComponentDefaults.createBottomNavigationStatePresenter(),
        ),
        content = BottomNavigationComponentDefaults.BottomNavigationComponentView
    )
}

fun createPlatformBridge(): IosBridge {
    return IosBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    )
}
