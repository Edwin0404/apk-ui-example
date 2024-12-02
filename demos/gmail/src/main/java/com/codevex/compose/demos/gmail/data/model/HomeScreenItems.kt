package com.codevex.compose.demos.gmail.data.model

sealed class HomeScreenItems {
    data object Dialogs : HomeScreenItems()
    data object TabLayout : HomeScreenItems()
    data object Carousel : HomeScreenItems()
    data object Layouts : HomeScreenItems()
    data class ListView(val type: String = "Vertical") : HomeScreenItems()
    data object AdvanceLists : HomeScreenItems()
    data object ConstraintsLayout : HomeScreenItems()
    data object CollapsingAppBar : HomeScreenItems()
    data object BottomAppBar : HomeScreenItems()
    data object BottomSheets : HomeScreenItems()
    data object Modifiers : HomeScreenItems()
    data object AndroidViews : HomeScreenItems()
    data object PullRefresh : HomeScreenItems()
    data object CustomFling : HomeScreenItems()
    data object MotionLayout : HomeScreenItems()

    val name: String
        get() = when (this) {
            is Dialogs -> "Dialogs"
            is TabLayout -> "TabLayout"
            is Carousel -> "Carousel"
            is ListView -> "$type ListView"
            ConstraintsLayout -> "Constraint Layout"
            MotionLayout -> "Motion Layout"
            CollapsingAppBar -> "Collapsing AppBar"
            BottomAppBar -> "BottomAppBar"
            BottomSheets -> "BottomSheets"
            Layouts -> "Layouts"
            Modifiers -> "Modifiers"
            AndroidViews -> "Compose X Android views"
            AdvanceLists -> "Advance Lists"
            PullRefresh -> "Pull refresh demos"
            CustomFling -> "Custom Fling"
        }
}