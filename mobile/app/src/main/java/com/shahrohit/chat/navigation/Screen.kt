package com.shahrohit.chat.navigation

sealed class Screen(val route : String) {
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object VerifyOtp : Screen("verify-otp/{email}/{username}"){
        fun createRoute(email: String, username: String) : String = "verify-otp/$email/$username"
    }
}