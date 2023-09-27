package com.pablichj.app.amadeusHotel.home

import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class HomeComponentViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault
) : TopBarComponentViewModelFactory<HomeComponentViewModel> {

    override fun create(
        topBarComponent: TopBarComponent<HomeComponentViewModel>
    ): HomeComponentViewModel {
        return HomeComponentViewModel(topBarComponent, topBarStatePresenter)
    }
}
