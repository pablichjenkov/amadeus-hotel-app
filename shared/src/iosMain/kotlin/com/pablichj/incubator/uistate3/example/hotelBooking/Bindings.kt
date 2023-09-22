package com.pablichj.incubator.uistate3.example.hotelBooking

import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.DefaultAppLifecycleDispatcher
import com.macaosoftware.platform.IosBridge
import com.pablichj.app.amadeusHotel.BottomNavigationDemoViewModel
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit
): UIViewController = IosComponentRender(
    rootComponent = rootComponent,
    iosBridge = iosBridge,
    onBackPress = onBackPress
)

fun buildAppComponent(): Component {
    return NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        componentViewModel = BottomNavigationDemoViewModel(),
        content = NavBarComponentDefaults.NavBarComponentView
    )
}

fun createPlatformBridge(): IosBridge {
    return IosBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    )
}
