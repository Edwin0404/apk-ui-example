package com.codevex.compose.demos.gmail.ui

import com.kiwi.navigationcompose.typed.Destination
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : Destination {

    @Serializable
    data object Home : Route

    @Serializable
    data class Detail(val emailUID: String) : Route
    @Serializable
    data object Create : Route
}
