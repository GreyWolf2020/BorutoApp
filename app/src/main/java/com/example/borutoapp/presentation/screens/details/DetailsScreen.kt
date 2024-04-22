package com.example.borutoapp.presentation.screens.details

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.borutoapp.ui.theme.titleColor
import com.example.borutoapp.util.Constants.BASE_URL
import com.example.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.borutoapp.util.PaletteGenerator.extractColorFromBitMap
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel
    ) {

    val selectedHero by detailsViewModel.selectedHero.collectAsState()
    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPaletter()
    }
    val context = LocalContext.current
    LaunchedEffect(true) {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageurl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    Log.d("DETAILSSCREEN", "$bitmap" )
                    if (bitmap != null) {
                        detailsViewModel.setColorPalette(
                            colors = extractColorFromBitMap(bitmap )
                        )
                    } else {
                        detailsViewModel.setColorPalette(
                            colors = mapOf(
                                "vibrant" to "#FFFFFF",
                                "darkVibrant" to "#FFFFFF",
                                "onDarkVibrant" to "#000000",
                            )
                        )
                    }
                }
            }
        }
    }
}