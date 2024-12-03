package com.codevex.compose.demos.gmail.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.LabelImportant
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.MarkunreadMailbox
import androidx.compose.material.icons.outlined.MoreTime
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun GmailDrawer(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item { DrawerHeader() }
        item { DividerWithPadding() }
        item { DrawerItem(icon = Icons.Filled.AllInbox, title = "All Inbox") }
        item { DividerWithPadding() }
        item { DrawerItem(icon = Icons.Outlined.Inbox, title = "Primary") }
        item { DrawerItem(icon = Icons.Outlined.Groups, title = "Social") }
        item { DrawerItem(icon = Icons.Outlined.LocalOffer, title = "Promotion") }
        item { DrawerCategory(title = "RECENT LABELS") }
        item { DrawerItem(icon = Icons.AutoMirrored.Outlined.Label, title = "[Imap]/Trash") }
        item { DrawerItem(icon = Icons.AutoMirrored.Outlined.Label, title = "facebook") }
        item { DrawerCategory(title = "ALL LABELS") }
        item { DrawerItem(icon = Icons.Outlined.StarBorder, title = "Starred") }
        item { DrawerItem(icon = Icons.Outlined.AccessTime, title = "Snoozed") }
        item { DrawerItem(icon = Icons.AutoMirrored.Outlined.LabelImportant, title = "Important", msgCount = "99+") }
        item { DrawerItem(icon = Icons.AutoMirrored.Outlined.Send, title = "Sent", msgCount = "99+") }
        item { DrawerItem(icon = Icons.Outlined.MoreTime, title = "Scheduled", msgCount = "99+") }
        item { DrawerItem(icon = Icons.Outlined.MarkunreadMailbox, title = "Outbox", msgCount = "10") }
    }
}

@Composable
fun DrawerHeader() {
    Text(
        text = "Gmail",
        color = Color.Red,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
}

@Composable
fun DividerWithPadding() {
    Column {
        HorizontalDivider(thickness = 0.3.dp)
        Spacer(modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun DrawerItem(icon: ImageVector, title: String, msgCount: String = "") {
    Row {
        Icon(imageVector = icon, modifier = Modifier.padding(16.dp), contentDescription = null)
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            text = title,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
        if (msgCount.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp),
                text = msgCount,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun DrawerCategory(title: String) {
    Text(
        text = title,
        letterSpacing = 0.7.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 12.sp,
        modifier = Modifier.padding(16.dp)
    )
}