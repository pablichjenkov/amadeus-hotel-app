package com.pablichj.app.amadeusHotel.login

import kotlinx.coroutines.flow.StateFlow

interface AuthManager {
    fun requestSignUp(email: String, password1: String, password2: String): StateFlow<SignUpRequestStatus>
    fun requestSignIn(email: String, password: String): StateFlow<SignInRequestStatus>
    fun requestRecoverPassword(email: String): StateFlow<RecoverPasswordRequestStatus>
    fun getCurrentToken(): String?
}