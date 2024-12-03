package com.codevex.compose.demos.gmail.ui.create

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun CreateMessageMoreActionPopupMenu() {
    var expanded by remember { mutableStateOf(false) }
    val menuItems: List<String> = listOf(
        "Schedule send",
        "Add from Contacts",
        "Confidential mode",
        "Save draft",
        "Discard",
        "Settings",
        "Help and feedback"
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset((-32).dp, (-32).dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(
                onClick = { },
                enabled = index != 3,
                text = { Text(item) }
            )
        }
    }
}