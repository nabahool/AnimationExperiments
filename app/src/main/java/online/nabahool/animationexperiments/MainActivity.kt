package online.nabahool.animationexperiments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import online.nabahool.animationexperiments.ui.theme.AnimationExperimentsTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InfiniteTransitionExperiment(
                        Modifier
                            .size(128.dp)
                            .padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationExperimentsTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // rememberInfiniteTransition
            Box(Modifier.size(128.dp)) {
                InfiniteTransitionExperiment(Modifier.size(128.dp))
            }

            // AnimatedVisibility
            var isVisible by remember { mutableStateOf(true) }
            Box(
                Modifier
                    .size(128.dp)
                    .clickable { isVisible = !isVisible }) {
                AnimatedVisibilityExperiment(Modifier.size(128.dp), isVisible)
            }

            // animateFloatAsState
            var setFloatToOne by remember { mutableStateOf(true) }
            val animatedAlpha by animateFloatAsState(
                targetValue = if (setFloatToOne) 1f else 0f,
                label = ""
            )
            Box(Modifier
                .size(128.dp)
                .clickable { setFloatToOne = !setFloatToOne }
                .graphicsLayer { alpha = animatedAlpha }
                .background(Color.Black))

            // animateIntOffsetAsState
            var setOffsetToZero by remember { mutableStateOf(true) }
            val pxToMove = with(LocalDensity.current) { 100.dp.toPx().roundToInt() }
            val offset by animateIntOffsetAsState(
                targetValue = if (setOffsetToZero) {
                    IntOffset.Zero
                } else {
                    IntOffset(pxToMove, 0)
                }, label = ""
            )
            Box(Modifier
                .size(128.dp)
                .clickable { setOffsetToZero = !setOffsetToZero }
                .offset { offset }
                .background(Color.Black))

            // animateContentSize()
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier
                .size(128.dp)
                .clickable(interactionSource = null, indication = null) {
                    expanded = !expanded
                }) {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .animateContentSize()
                        .size(if (expanded) 64.dp else 128.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            expanded = !expanded
                        }

                )
            }

            // AnimatedContent
            var slideBox by remember { mutableStateOf(true) }
            AnimatedContentExperiment(Modifier.size(128.dp)
                .clickable(interactionSource = null, indication = null) {
                    slideBox = !slideBox
                }, targetState = slideBox)

            
        }
    }
}