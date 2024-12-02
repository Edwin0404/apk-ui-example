package com.codevex.compose.demos.gmail.ui.details

import android.webkit.WebView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Redo
import androidx.compose.material.icons.automirrored.outlined.Reply
import androidx.compose.material.icons.automirrored.outlined.Undo
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.SubdirectoryArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.codevex.compose.demos.gmail.R
import com.github.javafaker.Faker
import java.util.concurrent.TimeUnit

data class Person(
    val name: String = Faker().name().firstName(),
    val email: String = Faker().internet().emailAddress(),
    @DrawableRes val avatar: Int = listOf(
        R.drawable.avatar_01,
        R.drawable.avatar_02,
        R.drawable.avatar_03,
        R.drawable.avatar_04,
        R.drawable.avatar_05,
        R.drawable.avatar_06,
        R.drawable.avatar_07,
        R.drawable.avatar_08,
        R.drawable.avatar_09,
        R.drawable.avatar_10,
        R.drawable.avatar_11
    ).random(),
)

data class Email(
    val subject: String = Faker().lorem().sentence(),
    val date: String = Faker().date().past(1, TimeUnit.DAYS).toString(),
    val isFavourite: Boolean = Faker().bool().bool(),
    val from: Person = Person(),
    val to: Person = Person(),
)

@Preview(showBackground = true)
@Composable
fun MessageDetailBody(
    email: Email = Email(),
    modifier: Modifier = Modifier,
) {
    var showExpandedEmailInfo by remember { mutableStateOf(true) }
    var isFavourite by remember { mutableStateOf(email.isFavourite) }

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            Row {
                Text(
                    text = email.subject,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                IconButton(onClick = { isFavourite = !isFavourite }) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Outlined.Star else Icons.Outlined.StarBorder,
                        contentDescription = null
                    )
                }
            }
        }
        item {
            Row {
                Image(
                    painter = painterResource(email.from.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                ) {
                    Row {
                        Text(text = email.from.name)
                        EmailText(
                            email = email.from.email,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.clickable {
                            showExpandedEmailInfo = !showExpandedEmailInfo
                        }
                    ) {
                        Text(text = "to me")
                        Icon(imageVector = Icons.Outlined.ExpandMore, contentDescription = null)
                    }
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.SubdirectoryArrowLeft,
                        contentDescription = null
                    )
                }
            }
        }
        item {
            if (showExpandedEmailInfo) {
                OutlinedCard(
                    modifier = Modifier.padding(16.dp, 8.dp),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        EmailInfoRow(
                            label = "From",
                            value = email.from.name,
                            email = email.from.email
                        )
                        EmailInfoRow(
                            label = "To",
                            value = email.to.name,
                            email = email.to.email
                        )
                        EmailInfoRow(
                            label = "Date",
                            value = email.date
                        )
                    }
                }
            }
        }
        item {
            val body = LoadAssetFileContent("email_body_02.html")
            AndroidView(
                factory = {
                    WebView(it).apply {
                        loadDataWithBaseURL(
                            null,
                            body,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                },
                modifier = Modifier.wrapContentHeight()
            )
        }
        item {
            Row {
                listOf(
                    Pair("Reply", Icons.AutoMirrored.Outlined.Undo),
                    Pair("Reply All", Icons.AutoMirrored.Outlined.Reply),
                    Pair("Forward", Icons.AutoMirrored.Outlined.Redo)
                ).map { (text, asset) ->
                    OutlinedButton(
                        onClick = {},
                        colors = ButtonDefaults.outlinedButtonColors().copy(
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            top = 12.dp,
                            end = 8.dp,
                            bottom = 12.dp
                        ),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                    ) {
                        Icon(imageVector = asset, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text(text = text, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun EmailInfoRow(label: String, value: String, email: String? = null) {
    Row {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = value, modifier = Modifier.padding(start = 8.dp))
        email?.let { EmailText(it, Modifier.padding(start = 8.dp)) }
    }
}

@Composable
fun EmailText(email: String, modifier: Modifier = Modifier) {
    Text(
        text = "<${email}>",
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontWeight = FontWeight.Thin
    )
}

@Composable
fun LoadAssetFileContent(fileName: String): String {
    val context = LocalContext.current
    return remember(fileName) {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
