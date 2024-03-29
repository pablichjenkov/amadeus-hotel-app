package com.pablichj.app.amadeusHotel.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordComponent(private val authManager: AuthManager) : Component() {

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun requestRecoverPassword(email: String, phone: String) {
        coroutineScope.launch {
            authManager.requestRecoverPassword(email).collect {
                when (val recoverPassReqStatus = it) {
                    is RecoverPasswordRequestStatus.RecoverPasswordFail -> TODO()
                    RecoverPasswordRequestStatus.RecoverPasswordInProgress -> TODO()
                    is RecoverPasswordRequestStatus.RecoverPasswordSuccess -> TODO()
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("ForgotPasswordComponent::Composing()")
        BackPressHandler()
        ForgotPasswordView { email ->
            authManager.requestRecoverPassword(email)
        }
    }

}
