package com.pablichj.incubator.uistate3.example.hotelBooking

import com.pablichj.app.amadeusHotel.AppBuilder
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.IosComponentRender
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.IosBridge
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
    return AppBuilder.buildGraph()
}

fun createPlatformBridge(): IosBridge {
    return IosBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    )
}
