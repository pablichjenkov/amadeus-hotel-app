package com.pablichj.app.amadeusHotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.AndroidBridge

class MainActivity : ComponentActivity() {

    val androidBridge = AndroidBridge()
    val bottomNavigationComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        componentViewModel = BottomNavigationDemoViewModel(),
        content = NavBarComponentDefaults.NavBarComponentView
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = bottomNavigationComponent,
                androidBridge = androidBridge,
                onBackPress = { finish() }
            )
        }
    }
}
