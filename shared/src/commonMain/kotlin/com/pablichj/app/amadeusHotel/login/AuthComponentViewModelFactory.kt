package com.pablichj.app.amadeusHotel.login

import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class AuthComponentViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault
) : TopBarComponentViewModelFactory<AuthComponentViewModel> {

    override fun create(
        topBarComponent: TopBarComponent<AuthComponentViewModel>
    ): AuthComponentViewModel {
        return AuthComponentViewModel(topBarComponent, topBarStatePresenter)
    }
}
