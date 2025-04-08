package com.shahrohit.chat.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.shahrohit.chat.R
import com.shahrohit.chat.presentation.common.AppLink
import com.shahrohit.chat.navigation.Screen
import com.shahrohit.chat.ui.theme.AppTypography
import com.shahrohit.chat.utils.DeviceFingerprint
import com.shahrohit.chat.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavController){
    val context = LocalContext.current
    val pages = listOf(
        OnboardingPage("Welcome!", "Secure and private messaging.", R.drawable.ic_secure),
        OnboardingPage("End-to-End Encryption", "Your messages are encrypted for safety.", R.drawable.ic_encryption),
        OnboardingPage("Get Started!", "Create an account or log in to continue.", R.drawable.ic_chat),
    )

    val pagerState = rememberPagerState {
        pages.size
    }

    val coroutineScope = rememberCoroutineScope()

    fun isLastPage() : Boolean {
        return (pagerState.currentPage == pages.size - 1)
    }

    fun navigateToLogin() {
        DeviceFingerprint.generate(context)
        PreferenceManager.setOnBoardingCompleted(true)
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Onboarding.route) { inclusive = true }
        }
    }

    fun onNextClick() {
        coroutineScope.launch {
            if(isLastPage()){
                navigateToLogin()
            }else {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    // *** UI ***
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Chat App", style = AppTypography.titleLarge)
            AppLink(text = "Skip", onClick = { navigateToLogin() })
        }

        HorizontalPager(state = pagerState) { page ->
            OnboardingPageUI(page = pages[page])
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onClick = { onNextClick() }
        ) {
            Text(
                style = AppTypography.titleMedium,
                text = if (isLastPage()) "Get Started" else "Next"
            )
        }
    }
}