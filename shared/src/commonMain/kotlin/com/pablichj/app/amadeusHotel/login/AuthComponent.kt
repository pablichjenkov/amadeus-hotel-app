package com.pablichj.app.amadeusHotel.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.pablichj.app.amadeusHotel.AuthorizeAPI
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthComponent : TopBarComponent<TopBarStatePresenterDefault>(
    TopBarComponent.createDefaultTopBarStatePresenter()
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val authManager: AuthManager = DefaultAuthManager(AuthorizeAPI())
    private val signInComponent = SignInComponent(authManager)
    private val signUpComponent = SignUpComponent(authManager)
    private val forgotPasswordComponent = ForgotPasswordComponent(authManager)

    init {
        signUpComponent.setParent(this)
        signInComponent.setParent(this)
        forgotPasswordComponent.setParent(this)

        coroutineScope.launch {
            signInComponent.outFlow.collect {
                when (it) {
                    SignInComponent.Out.SignUpClick -> {
                        backStack.push(signUpComponent)
                    }
                    SignInComponent.Out.ForgotPasswordClick -> {
                        backStack.push(forgotPasswordComponent)
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
            backStack.push(signInComponent)
        }
    }

    override fun onStart() {
        activeComponent.value?.dispatchStart()
    }

    override fun onStop() {
        activeComponent.value?.dispatchStop()
    }

    fun isUserLogin(): Boolean {
        return authManager.getCurrentToken().isNullOrEmpty().not()
    }

    override fun getStackBarItemForComponent(topComponent: Component): StackBarItem {
        val selectedNavItem = if (topComponent == signInComponent) {
            StackBarItem(
                label = "Sign In",
                icon = Icons.Default.Home,
            )
        } else {
            StackBarItem(
                label = "Search",
                icon = Icons.Default.Search,
            )
        }
        return selectedNavItem
    }

}
