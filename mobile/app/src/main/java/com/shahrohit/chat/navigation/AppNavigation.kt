package com.shahrohit.chat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shahrohit.chat.presentation.login.LoginScreen
import com.shahrohit.chat.presentation.onboarding.OnboardingScreen

@Composable
fun AppNavigation (navController: NavHostController, modifier: Modifier){
    val startDestination = "onboarding"

    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
    }
}