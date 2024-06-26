package com.example.borutoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.borutoapp.ui.theme.*

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ){
        items(count = 2){
            AnimatedShimmerEffect()
        }
    }
}

@Composable
fun AnimatedShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val alphaAnim = transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = alphaAnim.value)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(HERO_ITEM_HEIGHT),
        color = if (isSystemInDarkTheme())
            Color.Black else ShimmerLightGray,
        shape = RoundedCornerShape(size = LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth(0.5f)
                    .height(SHIMMER_NAME_PLACEHOLDER_HEIGHT),
                color = if (isSystemInDarkTheme())
                    ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(size = SMALL_PADDING)
            ) {}
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth()
                        .height(SHIMMER_ABOUT_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(size = EXTRA_EXTRA_SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(EXTRA_EXTRA_SMALL_PADDING))
            }
            Spacer(modifier = Modifier.padding(EXTRA_EXTRA_EXTRA_SMALL_PADDING))
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha)
                            .size(SHIMMER_RATING_PLACEHOLDER_HEIGHT),
                        color = if (isSystemInDarkTheme())
                            ShimmerDarkGray else ShimmerMediumGray,
                        shape = RoundedCornerShape(size = EXTRA_EXTRA_SMALL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
                }
            }
        }
    }
}

@Composable
@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ShimmerItemPreview() = AnimatedShimmerEffect()
