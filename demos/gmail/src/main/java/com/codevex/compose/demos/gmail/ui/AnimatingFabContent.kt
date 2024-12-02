package com.codevex.compose.demos.gmail.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import kotlin.math.roundToInt

/**
 * A layout that shows an icon and a text element used as the content for a FAB that extends with
 * an animation.
 */
@Composable
fun AnimatingFabContent(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    extended: Boolean = true,
) {
    val currentState = if (extended) ExpandableFabStates.Extended else ExpandableFabStates.Collapsed
    val transition = updateTransition(targetState = currentState, label = "transition")
    val duration = 200
    val opacityAnim = transition.animateFloat(
        transitionSpec = {
            when {
                ExpandableFabStates.Extended isTransitioningTo ExpandableFabStates.Collapsed ->
                    tween(
                        easing = LinearEasing,
                        durationMillis = (duration / 12f * 5).roundToInt() // 5 out of 12 frames
                    )

                ExpandableFabStates.Collapsed isTransitioningTo ExpandableFabStates.Extended -> {
                    tween(
                        easing = LinearEasing,
                        delayMillis = (duration / 3f).roundToInt(), // 4 out of 12 frames
                        durationMillis = (duration / 12f * 5).roundToInt() // 5 out of 12 frames
                    )
                }

                else -> snap()
            }
        }, label = "opacityAnim"
    ) {
        when (it) {
            ExpandableFabStates.Collapsed -> 0f
            ExpandableFabStates.Extended -> 1f
        }
    }

    val widthAnimation = transition.animateFloat(
        transitionSpec = {
            when {
                ExpandableFabStates.Extended isTransitioningTo ExpandableFabStates.Collapsed ->
                    tween(
                        easing = FastOutSlowInEasing,
                        durationMillis = duration
                    )

                ExpandableFabStates.Collapsed isTransitioningTo ExpandableFabStates.Extended -> {
                    tween(
                        easing = FastOutSlowInEasing,
                        durationMillis = duration
                    )
                }

                else -> snap()
            }
        }, label = "widthAnimation"
    ) {
        when (it) {
            ExpandableFabStates.Collapsed -> 0f
            ExpandableFabStates.Extended -> 1f
        }
    }

    // Using functions instead of Floats here can improve performance, preventing recompositions.
    IconAndTextRow(
        icon,
        text,
        { opacityAnim.value },
        { widthAnimation.value },
        modifier = modifier
    )
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop

@Composable
private fun IconAndTextRow(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    opacityProgress: () -> Float, // Functions instead of Floats, to slightly improve performance
    widthProgress: () -> Float,
    modifier: Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            icon()
            Box(modifier = Modifier.alpha(opacityProgress())) {
                text()
            }
        },
    ) { measurables, constraints ->

        val iconPlaceable = measurables[0].measure(constraints)
        val textPlaceable = measurables[1].measure(constraints)
        val height = constraints.maxHeight

        // FAB has an aspect ratio of 1 so the initial width is the height
        val initialWidth = height.toFloat()

        // Use it to get the padding
        val iconPadding = (initialWidth - iconPlaceable.width) / 2f

        // The full width will be : padding + icon + padding + text + padding
        val expandedWidth = iconPlaceable.width + textPlaceable.width + iconPadding * 3

        // Apply the animation factor to go from initialWidth to fullWidth
        val width = lerp(initialWidth, expandedWidth, widthProgress())

        layout(width.roundToInt(), height) {
            iconPlaceable.placeRelative(
                iconPadding.roundToInt(),
                constraints.maxHeight / 2 - iconPlaceable.height / 2
            )
            textPlaceable.placeRelative(
                (iconPlaceable.width + iconPadding * 2).roundToInt(),
                constraints.maxHeight / 2 - textPlaceable.height / 2
            )
        }
    }
}

private enum class ExpandableFabStates { Collapsed, Extended }
