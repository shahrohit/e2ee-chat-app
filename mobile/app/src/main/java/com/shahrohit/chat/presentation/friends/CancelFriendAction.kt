package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahrohit.chat.presentation.common.AppButton

@Composable
fun CancelFriendAction(
    responseText : String?,
    onCancelRequest: () -> Unit,
    isLoading : Boolean
) {
    AppButton(
        text = if(isLoading) "Cancelling..." else responseText ?: "Cancel Request",
        modifier = Modifier.fillMaxWidth(),
        onClick = onCancelRequest,
        enabled = !isLoading && responseText == null,
    )
}