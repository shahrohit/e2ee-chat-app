package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.shahrohit.chat.enums.FriendRequestTab
import com.shahrohit.chat.navigation.Screen
import com.shahrohit.chat.presentation.common.ColumnSpacer
import com.shahrohit.chat.presentation.common.EmptyListText
import com.shahrohit.chat.viewmodels.FriendViewModel

@Composable
fun FriendsScreen(navController: NavHostController, viewModel: FriendViewModel = hiltViewModel()) {

    val itemLoadingState = viewModel.itemLoadingState

    LaunchedEffect(viewModel.selectedTab) {
        when(viewModel.selectedTab){
            FriendRequestTab.Received -> viewModel.getReceivedFriendRequests()
            FriendRequestTab.Sent -> viewModel.getSentFriendRequests()
            FriendRequestTab.Friend -> viewModel.getFriends()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Top) {
        FriendScreenHeader(onClick = { navController.navigate(Screen.SearchFriend.route) })

        FriendRequestSection(selectedTab = viewModel.selectedTab,onTabSelected = viewModel::onTabSelected)

        ColumnSpacer()

        when(viewModel.selectedTab){

            FriendRequestTab.Friend -> {
                if(viewModel.friends.isEmpty()) EmptyListText("No Friends")
                else {
                    LazyColumn (verticalArrangement = Arrangement.spacedBy(15.dp)){
                        items(viewModel.friends) { FriendRequestCard(user = it, content = {}) }
                    }
                }
            }

            FriendRequestTab.Received -> {
                if(viewModel.receivedRequests.isEmpty()) EmptyListText("No Friend Request")
                else{
                    LazyColumn (verticalArrangement = Arrangement.spacedBy(15.dp)){
                        items(viewModel.receivedRequests) {
                            FriendRequestCard(user = it) {
                                RespondFriendAction(
                                    responseText = it.responseMessage,
                                    onReject = { viewModel.respondToFriendRequest(it.username, false) },
                                    onAccept = { viewModel.respondToFriendRequest(it.username, true) },
                                    isLoading = itemLoadingState[it.username] == true,
                                )
                            }
                        }
                    }
                }
            }

            FriendRequestTab.Sent -> {
                if(viewModel.sentRequests.isEmpty()) EmptyListText("No Friend Request")
                else{
                    LazyColumn (verticalArrangement = Arrangement.spacedBy(15.dp)){
                        items(viewModel.sentRequests) {
                            FriendRequestCard(user = it) {
                                CancelFriendAction(
                                    responseText = it.responseMessage,
                                    onCancelRequest = { viewModel.cancelFriendRequest(it.username) },
                                    isLoading = itemLoadingState[it.username] == true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}