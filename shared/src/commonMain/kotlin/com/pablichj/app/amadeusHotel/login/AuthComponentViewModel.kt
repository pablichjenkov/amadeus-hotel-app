package com.pablichj.app.amadeusHotel.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarItem
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault
import com.pablichj.app.amadeusHotel.AuthorizeAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthComponentViewModel(
    topBarComponent: TopBarComponent<AuthComponentViewModel>,
    override val topBarStatePresenter: TopBarStatePresenterDefault
) : TopBarComponentViewModel(topBarComponent) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val authManager: AuthManager = DefaultAuthManager(AuthorizeAPI())
    private val signInComponent = SignInComponent(authManager)
    private val signUpComponent = SignUpComponent(authManager)
    private val forgotPasswordComponent = ForgotPasswordComponent(authManager)

    override fun onAttach() {
        signUpComponent.setParent(topBarComponent)
        signInComponent.setParent(topBarComponent)
        forgotPasswordComponent.setParent(topBarComponent)

        coroutineScope.launch {
            signInComponent.outFlow.collect {
                when (it) {
                    SignInComponent.Out.SignUpClick -> {
                        topBarComponent.navigator.push(signUpComponent)
                    }

                    SignInComponent.Out.ForgotPasswordClick -> {
                        topBarComponent.navigator.push(forgotPasswordComponent)
                    }

                    SignInComponent.Out.LoginFail -> {
                        println("Pablo:: Login Fail")
                    }

                    SignInComponent.Out.LoginSuccess -> {
                        println("Pablo:: Login Success")
                    }
                }
            }
        }.invokeOnCompletion {
            if (it != null) {
                println("Pablo:: AuthComponent_Coroutine has been cancelled")
            }
        }

        if (isUserLogin().not()) {
            topBarComponent.navigator.push(signInComponent)
        }
    }

    override fun mapComponentToStackBarItem(topComponent: Component): TopBarItem {
        val selectedNavItem = if (topComponent == signInComponent) {
            TopBarItem(
                label = "Sign In",
                icon = Icons.Default.Home,
            )
        } else {
            TopBarItem(
                label = "Search",
                icon = Icons.Default.Search,
            )
        }
        return selectedNavItem
    }

    override fun onCheckChildForNextUriFragment(nextUriFragment: String): Component? {
        return null
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    fun isUserLogin(): Boolean {
        return authManager.getCurrentToken().isNullOrEmpty().not()
    }

}
