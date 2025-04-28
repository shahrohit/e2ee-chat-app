package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahrohit.chat.presentation.common.AppButton

@Composable
fun RespondFriendAction(
    responseText : String?,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    isLoading : Boolean = false,
) {
    if(isLoading || responseText != null){
        AppButton(
            text = if (isLoading) "Processing..." else responseText!! , modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            enabled = false
        )
    }
    else{
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            AppButton(text = "Confirm", modifier = Modifier.weight(0.5f), onClick = onAccept)

            AppButton(
                text = "Reject", modifier = Modifier.weight(0.5f),
                onClick = onReject, containerColor = MaterialTheme.colorScheme.surface,
            )
        }
    }
}