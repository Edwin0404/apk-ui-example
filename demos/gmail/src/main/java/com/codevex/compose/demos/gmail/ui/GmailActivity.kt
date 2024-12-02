package com.codevex.compose.demos.gmail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.codevex.compose.demos.gmail.ui.home.GmailScreen
import com.codevex.compose.demos.gmail.ui.theme.GmailTheme

class GmailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        setContent {
            GmailTheme {
                GmailScreen()
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, GmailActivity::class.java)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    GmailTheme {
        GmailScreen()
    }
}