package com.pablichj.app.amadeusHotel

import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault


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
