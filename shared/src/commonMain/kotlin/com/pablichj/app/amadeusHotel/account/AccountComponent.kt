package com.pablichj.app.amadeusHotel.account

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.app.amadeusHotel.login.AuthComponent
import com.pablichj.templato.component.core.Component

class AccountComponent : Component() {

    val authComponent = AuthComponent()

    init {
        authComponent.setParent(this)
    }

    override fun onStart() {
        authComponent.dispatchStart()
    }

    override fun onStop() {
        authComponent.dispatchStop()
    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (authComponent.isUserLogin().not()) {
            authComponent.Content(modifier)
        } else {
            Text("User Account Log-in")
        }

    }
}