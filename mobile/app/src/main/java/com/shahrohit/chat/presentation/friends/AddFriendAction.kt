package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahrohit.chat.presentation.common.AppButton

@Composable
fun AddFriendAction(
    responseText : String?,
    onAddFriend: () -> Unit,
    isLoading : Boolean = false,
) {
    AppButton(
        text = if(isLoading) "Sending.." else responseText ?: "Add Friend",
        modifier = Modifier.fillMaxWidth(),
        onClick = onAddFriend,
        enabled = !isLoading && responseText == null,
    )
}