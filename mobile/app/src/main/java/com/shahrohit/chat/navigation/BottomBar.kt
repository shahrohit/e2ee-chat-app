package com.shahrohit.chat.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    selectedIndex : Int,
    onItemSelected : (Int) -> Unit,
    items: List<HomeTab>
) {
    val borderColor = MaterialTheme.colorScheme.outlineVariant
    NavigationBar(
        modifier = Modifier.drawBehind {
            val strokeWidth = 1.dp.toPx()
            drawLine(
                color = borderColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidth
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp

    ) {
        items.forEachIndexed { index, screen ->

            val selected = selectedIndex == index

            val icon = when (screen) {
                is HomeTab.Chat -> if (selected) Icons.Filled.ChatBubble else Icons.Outlined.ChatBubble
                is HomeTab.Friends -> if (selected) Icons.Filled.People else Icons.Outlined.People
                is HomeTab.Call -> if (selected) Icons.Filled.Call else Icons.Outlined.Call
                is HomeTab.Profile -> if (selected) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle
            }

            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index) },
                icon = { Icon(icon, contentDescription = screen.route) },
                label = {
                    Text(
                        text = screen.route.replaceFirstChar { it.uppercase() },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                )
            )
        }
    }
}