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
    ModalDrawerSheet(drawerShape = RoundedCornerShape(0.dp)) {
        LazyColumn {
            item { DrawerHeader() }
            item { DividerWithPadding() }
            item { DrawerItem(Icons.Filled.AllInbox, "All Inbox", true) }
            item { DividerWithPadding() }
            item { DrawerItem(Icons.Outlined.Inbox, "Primary") }
            item { DrawerItem(Icons.Outlined.Groups, "Social") }
            item { DrawerItem(Icons.Outlined.LocalOffer, "Promotion") }
            item { DrawerCategory("RECENT LABELS") }
            item { DrawerItem(Icons.AutoMirrored.Outlined.Label, "[Imap]/Trash") }
            item { DrawerItem(Icons.AutoMirrored.Outlined.Label, "facebook") }
            item { DrawerCategory("ALL LABELS") }
            item { DrawerItem(Icons.Outlined.StarBorder, "Starred") }
            item { DrawerItem(Icons.Outlined.AccessTime, "Snoozed") }
            item { DrawerItemWithCount(Icons.AutoMirrored.Outlined.LabelImportant, "Important") }
            item { DrawerItemWithCount(Icons.AutoMirrored.Outlined.Send, "Sent") }
            item { DrawerItemWithCount(Icons.Outlined.MoreTime, "Scheduled") }
            item { DrawerItemWithCount(Icons.Outlined.MarkunreadMailbox, "Outbox", 10, 50) }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, title: String, selected: Boolean = false) {
    NavigationDrawerItem(
        shape = RoundedCornerShape(0.dp),
        label = { Text(title) },
        selected = selected,
        icon = { Icon(icon, null) },
        onClick = {}
    )
}

@Composable
fun DrawerItemWithCount(icon: ImageVector, title: String, minCount: Int = 90, maxCount: Int = 150) {
    val count = Faker().number().numberBetween(minCount, maxCount)
    NavigationDrawerItem(
        shape = RoundedCornerShape(0.dp),
        label = { Text(title) },
        icon = { Icon(icon, null) },
        onClick = {},
        selected = false,
        badge = { Text(if (count > 99) "99+" else count.toString()) }
    )
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
fun DrawerCategory(title: String) {
    Text(
        text = title,
        letterSpacing = 0.7.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 12.sp,
        modifier = Modifier.padding(16.dp)
    )
}