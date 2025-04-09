package com.shahrohit.chat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.presentation.common.showToast
import com.shahrohit.chat.presentation.connection.ConnectionScreen
import com.shahrohit.chat.presentation.home.HomeScreen
import com.shahrohit.chat.presentation.login.LoginScreen
import com.shahrohit.chat.presentation.onboarding.OnboardingScreen
import com.shahrohit.chat.presentation.register.RegisterScreen
import com.shahrohit.chat.presentation.verifyotp.VerifyOtpScreen
import com.shahrohit.chat.utils.AuthManager
import com.shahrohit.chat.utils.PreferenceManager

@Composable
fun AppNavigation (navController: NavHostController, modifier: Modifier){
    val context = LocalContext.current;
    LaunchedEffect(Unit) {
        AuthManager.authFailed.collect {
            showToast(context, "Session Expired, Please Login Again")
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    val startDestination = remember {
        when {
            PreferenceManager.isLoggedIn() -> Screen.Home.route
            PreferenceManager.isOnBoardingCompleted() -> Screen.Login.route
            else -> Screen.Onboarding.route
        }
    }
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier){
        composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Connection.route) { ConnectionScreen(navController) }
        composable(
            route = Screen.VerifyOtp.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType },
                navArgument("otpFor") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            if(email.isEmpty()) navController.popBackStack()

            val username = backStackEntry.arguments?.getString("username") ?: ""
            if(username.isEmpty()) navController.popBackStack()

            val otpForStr= backStackEntry.arguments?.getString("otpFor") ?: ""
            val otpFor = OtpFor.entries.find { it.name.equals(otpForStr, ignoreCase = true) };
            if(otpFor == null) navController.popBackStack()
            else{
                VerifyOtpScreen(navController, email, username, otpFor)
            }
        }

        composable(Screen.Home.route) { HomeScreen() }

    }
}