package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahrohit.chat.enums.FriendRequestTab

@Composable
fun FriendRequestSection(selectedTab : FriendRequestTab, onTabSelected : (FriendRequestTab) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {

        FriendRequestTab.entries.forEach { tab ->
            Button(
                onClick = { onTabSelected(tab) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(0.5f),
                colors = if (selectedTab == tab)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                else ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) { Text(tab.name) }
        }
    }
}

