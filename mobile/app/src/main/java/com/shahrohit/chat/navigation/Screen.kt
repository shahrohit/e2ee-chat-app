package com.shahrohit.chat.navigation

sealed class Screen(val route : String) {
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
}