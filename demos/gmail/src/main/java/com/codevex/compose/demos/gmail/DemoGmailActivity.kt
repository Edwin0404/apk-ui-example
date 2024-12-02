package com.codevex.compose.demos.gmail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.codevex.compose.demos.gmail.ui.home.GmailScreen
import com.codevex.compose.demos.gmail.ui.theme.GmailTheme
import kotlinx.serialization.ExperimentalSerializationApi

class DemoGmailActivity : ComponentActivity() {

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        setContent {
            GmailTheme {
                GmailScreen()
            }
        }
    }
}

@ExperimentalSerializationApi
@Preview
@Composable
fun ActivityPreview() {
    GmailTheme {
        GmailScreen()
    }
}