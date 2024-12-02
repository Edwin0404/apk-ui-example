package com.codevex.compose.demos.gmail.ui

import com.kiwi.navigationcompose.typed.Destination
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : Destination {

    @Serializable
    data object Home : Route

    @Serializable
    data object Detail : Route

    @Serializable
    data object Create : Route
}
