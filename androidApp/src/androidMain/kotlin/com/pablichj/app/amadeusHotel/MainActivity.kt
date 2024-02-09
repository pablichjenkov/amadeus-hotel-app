package com.pablichj.app.amadeusHotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults

class MainActivity : ComponentActivity() {

    val bottomNavigationComponent = BottomNavigationComponent(
        viewModelFactory = RootBottomNavigationDemoViewModelFactory(
            bottomNavigationStatePresenter = BottomNavigationComponentDefaults.createBottomNavigationStatePresenter(),
        ),
        content = BottomNavigationComponentDefaults.BottomNavigationComponentView
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = bottomNavigationComponent,
                onBackPress = { finish() }
            )
        }
    }
}
