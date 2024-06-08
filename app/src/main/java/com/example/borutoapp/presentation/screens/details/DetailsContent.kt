package com.example.borutoapp.presentation.screens.details

import android.app.Activity
import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.presentation.common.InfoBox
import com.example.borutoapp.presentation.components.OrderedList
import com.example.borutoapp.ui.theme.EXPANDED_RADIUS_LEVEL
import com.example.borutoapp.ui.theme.EXTRA_LARGE_PADDING
import com.example.borutoapp.ui.theme.INFO_ICON_SIZE
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.MIN_IMAGE_HEIGHT
import com.example.borutoapp.ui.theme.MIN_SHEET_HEIGHT
import com.example.borutoapp.ui.theme.REQUIRE_OFFSETMAX
import com.example.borutoapp.ui.theme.REQUIRE_OFFSETMIN
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.statusBar
import com.example.borutoapp.ui.theme.titleColor
import com.example.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.example.borutoapp.util.Constants.BASE_URL

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navController: NavHostController,
    selectedHero: Hero?,
    colors: Map<String, String> = mapOf("vibrant" to Integer.toHexString(MaterialTheme.colors.primary.toArgb()), "darkVibrant" to Integer.toHexString(MaterialTheme.colors.surface.toArgb()), "onDarkVibrant" to Integer.toHexString(MaterialTheme.colors.titleColor.toArgb()))
) {
    var vibrant by remember {
        mutableStateOf("#000000")
    }
    var darkVibrant by remember {
        mutableStateOf("#000000")
    }
    var onDarkVibrant by remember {
        mutableStateOf("#FFFFFF")
    }
    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }
    val activity = LocalContext.current as Activity
    val systemColor = MaterialTheme.colors.statusBar.toArgb()
    SideEffect {
        activity.window.statusBarColor = systemColor
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val topCornerDp by animateDpAsState(targetValue = if (scaffoldState.bottomSheetState.isCollapsed) EXTRA_LARGE_PADDING else EXPANDED_RADIUS_LEVEL)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetShape = RoundedCornerShape(
            topEnd = topCornerDp,
            topStart = topCornerDp
        ),
        sheetContent = {
            selectedHero?.let { hero ->
                BottomSheetContent(
                    selectedHero = hero,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant))
                )
            }
        },
        content = {
            selectedHero?.let { hero ->
                BackgroundContent(
                    heroImage = hero.image,
                    imageFraction = scaffoldState.currentSheetFraction,
                    onClosedClicked = navController::popBackStack,
                    backgroundColor = Color(parseColor(darkVibrant))
                )
            }
        }

    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetScaffoldState.topCornerRadius(): State<Dp> = animateDpAsState(
    targetValue = if (currentSheetFraction == 1f) EXTRA_LARGE_PADDING
    else
        EXPANDED_RADIUS_LEVEL
)

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor =contentColor
            )
            InfoBox(icon = painterResource(id = R.drawable.ic_calender),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor =contentColor
            )
            InfoBox(icon = painterResource(id = R.drawable.ic_cake),
            iconColor = infoBoxIconColor,
            bigText = selectedHero.day,
            smallText = stringResource(R.string.birthday),
            textColor =contentColor
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING)
                .fillMaxWidth(),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES,
            textAlign = TextAlign.Justify
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float,
    backgroundColor: Color = MaterialTheme.colors.background,
    onClosedClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${heroImage}"
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .error(R.drawable.ic_placeholder)
            .build()
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(imageFraction + MIN_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            painter = painter, 
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .padding(all = SMALL_PADDING),
                onClick = onClosedClicked
            ) {
                Icon(
                    modifier = Modifier
                        .size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = Color.White
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        Log.d("DetailsContent", "fraction = ${bottomSheetState.requireOffset().scale( REQUIRE_OFFSETMIN, REQUIRE_OFFSETMAX, 0f, 1f )}")
        return bottomSheetState.requireOffset().scale(  0f, 1f , REQUIRE_OFFSETMIN, REQUIRE_OFFSETMAX,)
    }

fun Float.scale(
    y1: Float,
    y2: Float,
    x1: Float,
    x2: Float
): Float {
    val m = (y1 - y2) / (x1 - x2)
    val c = m * (0 - x2) + y2
    return ((m * this) + c)
}

@Composable
@Preview
fun BottomSheetContentPreview() = BottomSheetContent(selectedHero = Hero(
    id = 11,
    name = "Momoshiki",
    image = "/images/momoshiki.jpg",
    about = "Momoshiki Ōtsutsuki (大筒木モモシキ, Ōtsutsuki Momoshiki) was a member of the Ōtsutsuki clan's main family, sent to investigate the whereabouts of Kaguya and her God Tree and then attempting to cultivate a new one out of the chakra of the Seventh Hokage. In the process of being killed by Boruto Uzumaki, Momoshiki placed a Kāma on him, allowing his spirit to remain intact through the mark.",
    rating = 3.9,
    power = 98,
    month = "Jan",
    day = "1st",
    family = listOf(
        "Otsutsuki Clan"
    ),
    abilities = listOf(
        "Rinnegan",
        "Byakugan",
        "Strength"
    ),
    natureTypes = listOf(
        "Fire",
        "Lightning",
        "Wind",
        "Water",
        "Earth"
    )
))

@Composable
@Preview
fun DetailsContentPreview() = DetailsContent(navController = rememberNavController(), selectedHero = Hero(
    id = 11,
    name = "Momoshiki",
    image = "/images/momoshiki.jpg",
    about = "Momoshiki Ōtsutsuki (大筒木モモシキ, Ōtsutsuki Momoshiki) was a member of the Ōtsutsuki clan's main family, sent to investigate the whereabouts of Kaguya and her God Tree and then attempting to cultivate a new one out of the chakra of the Seventh Hokage. In the process of being killed by Boruto Uzumaki, Momoshiki placed a Kāma on him, allowing his spirit to remain intact through the mark.",
    rating = 3.9,
    power = 98,
    month = "Jan",
    day = "1st",
    family = listOf(
        "Otsutsuki Clan"
    ),
    abilities = listOf(
        "Rinnegan",
        "Byakugan",
        "Strength"
    ),
    natureTypes = listOf(
        "Fire",
        "Lightning",
        "Wind",
        "Water",
        "Earth"
    )
))