package com.codevex.compose.ui.extensions

import androidx.compose.ui.graphics.Color

fun Color.Companion.random() = Color(
    red = (0..255).random() / 255f,
    green = (0..255).random() / 255f,
    blue = (0..255).random() / 255f,
    alpha = 1f
)

fun Color.Companion.randomPastel() = Color(
    red = randomPastelComponent(),
    green = randomPastelComponent(),
    blue = randomPastelComponent(),
    alpha = 1f
)

private fun randomPastelComponent() = ((255 / 4)..(255 / 3) * 3).random() / 255f