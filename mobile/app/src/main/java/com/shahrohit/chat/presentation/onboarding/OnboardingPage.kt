package com.shahrohit.chat.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahrohit.chat.ui.theme.AppTypography

data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun OnboardingPageUI(page : OnboardingPage) {
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ){
        Image(painter = painterResource(id = page.imageRes), contentDescription = null)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.title, style = AppTypography.titleLarge, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = page.description, style = AppTypography.bodyLarge, textAlign = TextAlign.Center)
    }
}