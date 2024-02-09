package com.pablichj.app.amadeusHotel.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.pablichj.app.amadeusHotel.login.AuthComponentViewModel
import com.pablichj.app.amadeusHotel.login.AuthComponentViewModelFactory

class AccountComponent : Component() {

    private val authComponent = TopBarComponent(
        viewModelFactory = AuthComponentViewModelFactory(
            TopBarComponentDefaults.createTopBarStatePresenter()
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    )

    val authComponentViewModel = authComponent.componentViewModel

    init {
        authComponent.setParent(this)
    }

    override fun onActive() {
        authComponent.dispatchActive()
    }

    override fun onInactive() {
        authComponent.dispatchInactive()
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
