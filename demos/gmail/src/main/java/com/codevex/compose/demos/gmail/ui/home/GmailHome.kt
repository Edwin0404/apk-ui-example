package com.codevex.compose.demos.gmail.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codevex.compose.demos.gmail.ui.Route
import com.codevex.compose.demos.gmail.ui.create.CreateMessageScreen
import com.codevex.compose.demos.gmail.ui.details.Email
import com.codevex.compose.demos.gmail.ui.details.MessageDetailScreen
import com.codevex.compose.demos.gmail.ui.details.Person
import com.codevex.compose.demos.gmail.ui.theme.green500
import com.github.javafaker.Faker
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.math.absoluteValue
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigate

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun GmailScreen() {
    val navController = rememberNavController()
    val user = Person()
    val emails = (0..20).map { Email().copy(to = user) }

    NavHost(
        navController = navController,
        startDestination = createRoutePattern<Route.Home>()
    ) {
        composable<Route.Home> {
            GmailHome(
                navController = navController,
                emails = emails
            )
        }

        composable<Route.Detail> {
            MessageDetailScreen(
                navController = navController,
                email = emails.first { it.uid == emailUID }
            )
        }

        composable<Route.Create> {
            CreateMessageScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun GmailHome(
    navController: NavHostController,
    emails: List<Email>,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val fabExpandState = remember { mutableStateOf(true) }
    var showUserDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.kiwiNavigate(Route.Create) },
                expanded = fabExpandState.value,
                icon = { Icon(Icons.Filled.Edit, null) },
                text = { Text("Compose") },
            )
        },
        drawerContent = { GmailDrawer() },
        drawerBackgroundColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        scaffoldState = scaffoldState,
        bottomBar = {
            NavigationBar(modifier = Modifier.height(60.dp)) {
                BottomNavigationItem(
                    icon = {
                        IconWithBadge(
                            badge = emails.filter { it.isFavourite }.size,
                            icon = Icons.Outlined.Mail
                        )
                    },
                    onClick = {},
                    selected = true,
                    label = { Text("Mail") },
                )

                BottomNavigationItem(
                    icon = {
                        IconWithBadge(badge = 0, icon = Icons.Outlined.Call)
                    },
                    onClick = { },
                    selected = false,
                    label = { Text("Meet") },
                )
            }
        }
    ) { paddingValues ->
        GmailContent(
            emails = emails,
            fabExpandState = fabExpandState,
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            onMenuClicked = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            },
            onAvatarClicked = { showUserDialog = true },
        )
    }

    if (showUserDialog)
        UserEmailDialog(emails.first().to) { showUserDialog = false }
}

@Composable
fun IconWithBadge(badge: Int, icon: ImageVector) {
    BadgedBox(
        badge = {
            if (badge > 0) {
                Badge { Text("$badge") }
            }
        }
    ) {
        Icon(icon, null)
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun GmailContent(
    fabExpandState: MutableState<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onMenuClicked: () -> Unit = {},
    onAvatarClicked: () -> Unit = {},
    emails: List<Email>,
) {
    val lazyListState = rememberLazyListState()

    var offsetY by remember { mutableIntStateOf(0) }
    var oldIndex by remember { mutableIntStateOf(0) }
    var searchOffsetY by remember { mutableIntStateOf(0) }

    val searchLayoutHeightPx = with(LocalDensity.current) { 70.dp.toPx() }

    // ensures that the user intents to have scroll gesture..
    val isVisibleScrolled =
        oldIndex != lazyListState.firstVisibleItemIndex ||
                (offsetY - lazyListState.firstVisibleItemScrollOffset).absoluteValue > 15

    println("${lazyListState.firstVisibleItemIndex}  ${lazyListState.firstVisibleItemScrollOffset}")

    if (isVisibleScrolled) {
        when {
            oldIndex > lazyListState.firstVisibleItemIndex -> {   // down
                fabExpandState.value = true
            }

            oldIndex < lazyListState.firstVisibleItemIndex -> {  // up
                fabExpandState.value = false
            }

            oldIndex == lazyListState.firstVisibleItemIndex -> {
                fabExpandState.value = offsetY > lazyListState.firstVisibleItemScrollOffset
            }
        }

        // for the initial search offset
        if (lazyListState.firstVisibleItemIndex == 0
            && lazyListState.firstVisibleItemScrollOffset < searchLayoutHeightPx
            && !fabExpandState.value
        ) {
            searchOffsetY = -lazyListState.firstVisibleItemScrollOffset
        } else if (fabExpandState.value) {
            searchOffsetY = 0
        } else if (!fabExpandState.value) {
            searchOffsetY = (-searchLayoutHeightPx).toInt()
        }
    }

    offsetY = lazyListState.firstVisibleItemScrollOffset
    oldIndex = lazyListState.firstVisibleItemIndex

    Box(modifier = modifier) {
        LazyColumn(state = lazyListState) {
            item {
                Spacer(modifier = Modifier.height(72.dp))
            }

            items(emails) {
                Box(modifier = Modifier.background(green500)) {
                    GmailListItem(it) {
                        navController.kiwiNavigate(Route.Detail(it.uid))
                    }
                }
            }
        }

        SearchLayout(
            offset = searchOffsetY,
            onAvatarClicked = onAvatarClicked,
            avatarRes = emails.first().to.avatar,
            onMenuClicked = onMenuClicked
        )
    }
}

@Composable
@Preview
fun UserEmailDialog(user: Person = Person(), onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Outlined.Close, null)
                    }

                    Text(
                        text = "Google",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }

                GmailUserEmail(user, Faker().number().numberBetween(0, 100))

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.outlinedButtonColors().copy(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Manage your Google Account")
                }

                HorizontalDivider()

                (0..(2..4).random()).forEach { _ ->
                    GmailUserEmail(Person(), Faker().number().numberBetween(0, 100))
                }

                listOf(
                    Pair("Add another account", Icons.Default.PersonAdd),
                    Pair("Manage accounts on this device", Icons.Default.AccountCircle)
                ).forEach { (text, icon) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                        )

                        Text(text, Modifier.padding(start = 16.dp))
                    }
                }

                HorizontalDivider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Privacy Policy",
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(text = " • ")
                    Text(
                        text = "Terms of service",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@Composable
fun GmailUserEmail(person: Person, badgeCount: Int) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Image(
            painter = painterResource(person.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable {}
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(person.name)

            Row {
                Text(
                    text = "<${person.email}>",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Thin
                )

                Text(text = "$badgeCount")
            }
        }

    }
}