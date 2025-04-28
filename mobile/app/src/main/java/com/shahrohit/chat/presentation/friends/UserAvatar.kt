package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shahrohit.chat.R

@Composable
fun UserAvatar(imageUrl : String?, description : String? = null) {
    val validImage = !imageUrl.isNullOrBlank()
    val defaultImage = if(validImage)  null else painterResource(R.drawable.user);

    if(validImage){
        AsyncImage(
            model = imageUrl,
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(96.dp).clip(CircleShape)
        )
    }
    else{
        Image(
            painter = defaultImage!!,
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(96.dp).clip(CircleShape)
        )
    }
}