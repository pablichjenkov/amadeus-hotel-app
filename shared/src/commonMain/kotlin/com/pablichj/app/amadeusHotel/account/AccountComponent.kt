package com.pablichj.app.amadeusHotel.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.pablichj.app.amadeusHotel.login.AuthComponentViewModel

class AccountComponent : Component() {

    val authComponentViewModel = AuthComponentViewModel()

    val authComponent = TopBarComponent(
        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
        componentViewModel = authComponentViewModel,
        content = TopBarComponentDefaults.TopBarComponentView
    )

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
        if (authComponentViewModel.isUserLogin().not()) {
            authComponent.Content(modifier)
        } else {
            Text("User Account Log-in")
        }
    }

}
