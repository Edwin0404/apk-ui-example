package com.codevex.compose.demos.gmail.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.javafaker.Faker

@Preview
@Composable
fun GmailDrawer() {
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(0.dp)
    ) {
        LazyColumn {
            item { DrawerHeader() }
            item { DividerWithPadding() }
            item { DrawerItem(icon = Icons.Filled.AllInbox, selected = true, title = "All Inbox") }
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
            item {
                DrawerItem(
                    icon = Icons.AutoMirrored.Outlined.LabelImportant,
                    title = "Important",
                    count = Faker().number().numberBetween(90, 150)
                )
            }
            item {
                DrawerItem(
                    icon = Icons.AutoMirrored.Outlined.Send,
                    title = "Sent",
                    count = Faker().number().numberBetween(90, 150)
                )
            }
            item {
                DrawerItem(
                    icon = Icons.Outlined.MoreTime,
                    title = "Scheduled",
                    count = Faker().number().numberBetween(90, 150)
                )
            }
            item {
                DrawerItem(
                    icon = Icons.Outlined.MarkunreadMailbox,
                    title = "Outbox",
                    count = Faker().number().numberBetween(10, 50)
                )
            }
        }
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
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    title: String,
    selected: Boolean = false,
    count: Int = 0,
) {
    NavigationDrawerItem(
        shape = RoundedCornerShape(0.dp),
        label = { Text(title) },
        selected = selected,
        icon = { Icon(icon, null) },
        onClick = {},
        badge = when (count) {
            0 -> null
            else -> {
                {
                    Text(
                        when {
                            count > 99 -> "99+"
                            else -> count.toString()
                        }
                    )
                }
            }
        },
    )
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