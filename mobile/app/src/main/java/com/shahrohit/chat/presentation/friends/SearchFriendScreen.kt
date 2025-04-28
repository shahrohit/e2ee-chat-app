package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahrohit.chat.enums.FriendStatus
import com.shahrohit.chat.presentation.common.AppButton

import com.shahrohit.chat.presentation.common.AppTextField
import com.shahrohit.chat.presentation.common.ColumnSpacer
import com.shahrohit.chat.presentation.common.EmptyListText
import com.shahrohit.chat.viewmodels.SearchFriendViewModel

@Composable
fun SearchFriendScreen(viewModel: SearchFriendViewModel = hiltViewModel()){
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Friends", style = MaterialTheme.typography.titleLarge)
        ColumnSpacer(height = 16)
        AppTextField(
            value = viewModel.query,
            onChange = viewModel::onQueryChange,
            placeholder = "Search...",
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
        )

        ColumnSpacer(height = 16)

        if(viewModel.checkingStatus == true) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).size(20.dp))
        }
        else if(viewModel.checkingStatus == false){
            if(viewModel.users.isEmpty()) EmptyListText("No User Found")
            else {
                LazyColumn (verticalArrangement = Arrangement.spacedBy(15.dp)){
                    items(viewModel.users) {
                        FriendRequestCard(user = it) {
                            if(it.friendStatus == FriendStatus.NONE){
                                AddFriendAction(
                                    responseText = it.responseMessage,
                                    isLoading = viewModel.itemLoadingState[it.username] == true,
                                    onAddFriend = {viewModel.sendFriendRequest(it.username) },
                                )
                            }else{
                                AppButton(
                                    text = "Request Sent",
                                    enabled = false,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
       }
    }
}