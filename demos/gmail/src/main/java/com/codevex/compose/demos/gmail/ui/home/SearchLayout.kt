package com.codevex.compose.demos.gmail.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codevex.compose.demos.gmail.R
import com.codevex.compose.demos.gmail.ui.theme.graySurface

@Composable
@Preview(showBackground = true)
fun SearchLayoutPreview() {
    SearchLayout(offset = 0, avatarRes = R.drawable.avatar_03)
}

@Composable
fun SearchLayout(
    onMenuClicked: () -> Unit = {},
    onAvatarClicked: () -> Unit = {},
    @DrawableRes avatarRes: Int,
    offset: Int,
) {
    val background = if (isSystemInDarkTheme()) graySurface else Color.White.copy(alpha = 0.8f)
    var value by remember { mutableStateOf(TextFieldValue()) }

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.1.dp, Color.LightGray),
        modifier = Modifier
            .padding(8.dp)
            .graphicsLayer(translationY = offset.toFloat()),
        colors = CardDefaults.cardColors().copy(
            containerColor = background
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMenuClicked) {
                Icon(Icons.Outlined.Menu, null)
            }

            TextField(
                value = value,
                placeholder = { Text("Search in emails") },
                onValueChange = { value = it },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                ),
                singleLine = true,
            )

            IconButton(onClick = onAvatarClicked) {
                Image(
                    painter = painterResource(avatarRes),
                    contentDescription = stringResource(id = R.string.cd_gmail_profile),
                    modifier = Modifier.clip(CircleShape)
                )
            }
        }
    }
}




