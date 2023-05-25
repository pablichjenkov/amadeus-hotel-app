package com.pablichj.app.amadeusHotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.app.amadeusHotel.AppBuilder
import com.pablichj.templato.component.core.AndroidComponentRender

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = AppBuilder.buildGraph(),
                onBackPressEvent = { finish() }
            )
        }
    }
}