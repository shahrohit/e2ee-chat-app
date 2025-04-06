package com.shahrohit.chat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shahrohit.chat.presentation.home.HomeScreen
import com.shahrohit.chat.presentation.login.LoginScreen
import com.shahrohit.chat.presentation.onboarding.OnboardingScreen
import com.shahrohit.chat.presentation.register.RegisterScreen
import com.shahrohit.chat.presentation.verifyotp.VerifyOtpScreen
import com.shahrohit.chat.utils.PreferenceManager

@Composable
fun AppNavigation (navController: NavHostController, modifier: Modifier){
    val startDestination = remember {
        if (PreferenceManager.isOnBoardingCompleted()) {
            Screen.Login.route
        } else {
            Screen.Onboarding.route
        }
    }
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(
            route = Screen.VerifyOtp.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val username = backStackEntry.arguments?.getString("username") ?: ""
            VerifyOtpScreen(navController, email, username)
        }
    }
}