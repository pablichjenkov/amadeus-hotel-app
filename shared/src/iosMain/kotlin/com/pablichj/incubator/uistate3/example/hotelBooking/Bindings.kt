package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults
import com.macaosoftware.component.core.Component
import com.pablichj.app.amadeusHotel.RootBottomNavigationDemoViewModelFactory
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    onBackPress: () -> Unit
): UIViewController = ComposeUIViewController {
    IosComponentRender(
        rootComponent = rootComponent,
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
