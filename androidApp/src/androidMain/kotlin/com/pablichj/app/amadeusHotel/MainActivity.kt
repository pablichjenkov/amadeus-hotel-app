package com.pablichj.app.amadeusHotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.templato.component.core.AndroidComponentRender
import com.pablichj.templato.component.platform.AndroidBridge

class MainActivity : ComponentActivity() {
    val androidBridge = AndroidBridge()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = AppBuilder.buildGraph(),
                androidBridge = androidBridge,
                onBackPress = { finish() }
            )
        }
    }
}
