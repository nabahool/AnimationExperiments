package online.nabahool.animationexperiments

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntOffset

@Composable
fun InfiniteTransitionExperiment(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")
    val color by infiniteTransition.animateColor(
        Color.Black,
        Color.DarkGray,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorInfiniteTransition"
    )
    val float by infiniteTransition.animateFloat(
        1.0f,
        0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatInfiniteTransition")

    val value by infiniteTransition.animateValue(
        DpOffset(0.dp, 0.dp),
        DpOffset(64.dp, 64.dp),
        typeConverter = DpOffset.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "valueInfiniteTransition"
    )

    Box(modifier = modifier
        .background(color = color)
        .alpha(float)
        .absoluteOffset(value.x, value.y)
    ) {
        Text("hi")
    }
}

@Composable
fun AnimatedVisibilityExperiment(modifier: Modifier = Modifier, isVisible: Boolean = true) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(modifier = Modifier.background(Color.Black)) {
            Text(modifier = Modifier.animateEnterExit(slideIn { IntOffset(it.width/2, it.height/2) }, ), text = "hi")
        }
    }

}

@Composable
fun AnimatedContentExperiment(modifier: Modifier = Modifier, targetState: Boolean) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            if (targetState) {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn() togetherWith
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeOut()

//                slideInHorizontally { width -> width } + fadeIn() togetherWith
//                        slideOutHorizontally { width -> -width } + fadeOut()
            } else {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeIn() togetherWith
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut()

//                slideInHorizontally { width -> -width } + fadeIn() togetherWith
//                        slideOutHorizontally { width -> width } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }, label = ""
    ) { targetState ->
        val color = if (targetState) { Color.Black } else { Color.Blue }
        Box(modifier = Modifier.background(color))
    }
}