package com.shahrohit.chat.navigation

sealed class Screen(val route : String) {
    data object Onboarding : Screen("onboarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object VerifyOtp : Screen("verify-otp/{email}/{username}/{otpFor}"){
        fun createRoute(email: String, username: String, otpFor: String) : String = "verify-otp/$email/$username/$otpFor"
    }
    data object Connection : Screen("connection")
    data object SearchFriend : Screen("search-friend")
}

sealed class HomeTab(val route: String){
    data object Chat : HomeTab("chat")
    data object Friends : HomeTab("friends")
    data object Call : HomeTab("call")
    data object Profile : HomeTab("profile")

}