package com.pablichj.app.amadeusHotel

import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault

class RootBottomNavigationDemoViewModelFactory(
    private val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModelFactory<RootBottomNavigationDemoViewModel> {

    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<RootBottomNavigationDemoViewModel>
    ): RootBottomNavigationDemoViewModel {
        return RootBottomNavigationDemoViewModel(
            bottomNavigationComponent = bottomNavigationComponent,
            bottomNavigationStatePresenter = bottomNavigationStatePresenter
        )
    }
}
