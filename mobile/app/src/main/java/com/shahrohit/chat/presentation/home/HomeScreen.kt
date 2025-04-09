package com.shahrohit.chat.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.shahrohit.chat.navigation.BottomBar
import com.shahrohit.chat.navigation.HomeTab
import com.shahrohit.chat.presentation.call.CallScreen
import com.shahrohit.chat.presentation.chat.ChatScreen
import com.shahrohit.chat.presentation.friends.FriendsScreen
import com.shahrohit.chat.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(){
    val pages = listOf(HomeTab.Chat, HomeTab.Friends, HomeTab.Call, HomeTab.Profile)
    val pagerState = rememberPagerState {
        pages.size
    }
    val scope = rememberCoroutineScope()

    BackHandler (enabled = pagerState.currentPage != 0) {
        scope.launch { pagerState.scrollToPage(0) }
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedIndex = pagerState.currentPage,
                onItemSelected = { index ->
                    scope.launch { pagerState.scrollToPage(index) }
                },
                items = pages
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (pages[page]) {
                is HomeTab.Chat -> ChatScreen()
                is HomeTab.Friends -> FriendsScreen()
                is HomeTab.Call -> CallScreen()
                is HomeTab.Profile -> ProfileScreen()
            }
        }
    }
}