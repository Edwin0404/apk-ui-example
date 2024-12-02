package com.codevex.compose.demos.gmail.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codevex.compose.demos.gmail.R
import com.codevex.compose.demos.gmail.data.DemoDataProvider
import com.codevex.compose.demos.gmail.ui.AnimatingFabContent
import com.codevex.compose.demos.gmail.ui.Route
import com.codevex.compose.demos.gmail.ui.create.CreateMessageScreen
import com.codevex.compose.demos.gmail.ui.details.MessageDetailScreen
import com.codevex.compose.demos.gmail.ui.theme.graySurface
import com.codevex.compose.demos.gmail.ui.theme.green500
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.math.absoluteValue
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigate

@ExperimentalSerializationApi
@Composable
fun GmailScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = createRoutePattern<Route.Home>()
    ) {
        composable<Route.Home> {
            GmailHome(navController = navController)
        }

        composable<Route.Detail> {
            MessageDetailScreen(navController = navController)
        }

        composable<Route.Create> {
            CreateMessageScreen(navController = navController)
        }
    }
}

@ExperimentalSerializationApi
@Composable
fun GmailHome(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val fabExpandState = remember { mutableStateOf(false) }
    val showUserDialog = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            GmailFloatingActionButton(navController, fabExpandState.value)
        },
        drawerContent = { GmailDrawer() },
        drawerBackgroundColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            GmailContent(
                fabExpandState = fabExpandState,
                scaffoldState = scaffoldState,
                navController = navController,
                showUserDialog = showUserDialog,
                modifier = Modifier.padding(paddingValues),
            )
        },
        bottomBar = {
            val background = if (isSystemInDarkTheme()) graySurface else Color.White
            BottomNavigation(
                backgroundColor = background
            ) {
                BottomNavigationItem(
                    icon = {
                        IconWithBadge(badge = 1, icon = Icons.Outlined.Mail)
                    },
                    onClick = {
                    },
                    selected = true,
                    label = { Text("Mail") },
                )

                BottomNavigationItem(
                    icon = {
                        IconWithBadge(badge = 0, icon = Icons.Outlined.Call)
                    },
                    onClick = {
                    },
                    selected = true,
                    label = { Text("Meet") },
                )

            }
        },
    )

    UserEmailDialog(showUserDialog)
}

@Composable
fun IconWithBadge(badge: Int, icon: ImageVector, modifier: Modifier = Modifier) {

    Box(modifier = Modifier.size(36.dp)) {
        Icon(
            imageVector = icon,
            modifier = modifier.align(
                Alignment.BottomCenter
            ),
            contentDescription = null
        )

        if (badge != 0) {
            Text(
                text = "$badge",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd)
                    .size(16.dp)
            )
        }
    }

}

@ExperimentalSerializationApi
@Composable
fun GmailFloatingActionButton(navController: NavHostController, expandState: Boolean) {

    FloatingActionButton(
        onClick = {
            navController.kiwiNavigate(Route.Create)
        },
        modifier = Modifier
            .padding(16.dp)
            .height(48.dp)
            .widthIn(min = 48.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        AnimatingFabContent(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(id = R.string.cd_create_new_email)
                )
            },
            text = {
                Text(
                    text = "Compose",
                    modifier = Modifier.padding(end = 8.dp)
                )
            },
            extended = expandState
        )
    }

}

@ExperimentalSerializationApi
@Composable
fun GmailContent(
    fabExpandState: MutableState<Boolean>,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    showUserDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {

    val tweets = remember { DemoDataProvider.tweetList }

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

            items(tweets) {
                val visible = remember(it.id) { mutableStateOf(true) }

                AnimatedVisibility(visible = visible.value) {
                    Box(modifier = Modifier.background(green500)) {
                        GmailListActionItems(modifier = Modifier.align(Alignment.CenterEnd))
                        GmailListItem(it) {
                            navController.kiwiNavigate(Route.Detail)
                        }
                    }
                }
            }
        }

        SearchLayout(searchOffsetY, scaffoldState.drawerState, showUserDialog) {
            navController.kiwiNavigate(Route.Create)
        }
    }
}

@Composable
fun UserEmailDialog(showUserDialog: MutableState<Boolean>) {

    val background = if (isSystemInDarkTheme()) graySurface else Color.White

    if (showUserDialog.value) {
        Dialog(
            onDismissRequest = {
                showUserDialog.value = false
            }
        ) {

            Surface(
                modifier = Modifier,
                shape = MaterialTheme.shapes.medium,
                color = background,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {

                Column {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { showUserDialog.value = false }) {
                            Icon(Icons.Outlined.Close, contentDescription = null)
                        }

                        Text(
                            text = "Google",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }

                    GmailUserEmail(R.drawable.p1, "Subash Aryc", "subash@gmail.com", 2)

                    Text(
                        text = "Manage your Google Account",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(8.dp)
                            .border(1.dp, Color.Gray.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {}
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    GmailUserEmail(
                        imageId = R.drawable.p2,
                        name = "Subash ",
                        email = "aryal.subash@yahoo.com",
                        badgeCount = 39
                    )
                    GmailUserEmail(
                        imageId = R.drawable.p2,
                        name = "Subash Zi ",
                        email = "subashz@gmail.com",
                        badgeCount = 10
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(8.dp),
                            contentDescription = null
                        )

                        Text(
                            text = "Add another account",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(8.dp),
                            contentDescription = null
                        )
                        Text(
                            text = "Manage accounts on this device",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Privacy Policy",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {}
                                .padding(8.dp)
                        )
                        Text(
                            text = "•"
                        )
                        Text(
                            text = "Terms of service",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {}
                                .padding(8.dp)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GmailUserEmail(imageId: Int, name: String, email: String, badgeCount: Int) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
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
            Text(text = name)

            Row {
                Text(
                    text = email,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "$badgeCount",
                    fontSize = 12.sp
                )
            }
        }

    }
}