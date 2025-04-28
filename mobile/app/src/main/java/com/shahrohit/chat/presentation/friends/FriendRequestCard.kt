package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.shahrohit.chat.presentation.common.RowSpacer
import com.shahrohit.chat.remote.dto.UserProfile

@Composable
fun FriendRequestCard(
    user: UserProfile,
    content : @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(user.profilePictureUrl, user.username)
        RowSpacer()
        Column {
            UserInformation(user.name, user.username)
            content()
        }
    }
}