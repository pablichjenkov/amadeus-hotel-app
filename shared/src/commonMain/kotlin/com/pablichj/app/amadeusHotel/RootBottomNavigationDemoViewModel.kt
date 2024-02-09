package com.pablichj.app.amadeusHotel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModel
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.pablichj.app.amadeusHotel.account.AccountComponent
import com.pablichj.app.amadeusHotel.home.HomeComponentViewModelFactory

class RootBottomNavigationDemoViewModel(
    bottomNavigationComponent: BottomNavigationComponent<RootBottomNavigationDemoViewModel>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModel(bottomNavigationComponent) {

    private val homeComponent = TopBarComponent(
        viewModelFactory = HomeComponentViewModelFactory(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    )


    val favoriteComponent = object : Component() {
        @Composable
        override fun Content(modifier: Modifier) {
            Text("Missing Implementation")
        }
    }
    val accountComponent = AccountComponent()

    override fun onAttach() {
        val navBarItems = createNavBarItems()
        val selectedIndex = 0
        bottomNavigationComponent.setNavItems(navBarItems, selectedIndex)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDetach() {
    }

    private fun createNavBarItems(): MutableList<NavItem> {
        return mutableListOf(
            NavItem(
                component = homeComponent,
                label = "Home",
                icon = Icons.Default.Home,
            ),
            NavItem(
                label = "Favorites",
                icon = Icons.Default.Favorite,
                component = favoriteComponent,//TopBarComponent(screenIcon = Icons.Default.Favorite),
            ),
            NavItem(
                label = "Account",
                icon = Icons.Default.AccountCircle,
                component = accountComponent
            )
        )
    }

}
