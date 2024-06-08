package com.example.borutoapp.presentation.screens.home

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.ui.theme.statusBar

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    (LocalContext.current as Activity).window.statusBarColor = MaterialTheme.colors.statusBar.toArgb()
    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = { paddingValues ->
            ListContent(
                modifier = Modifier.padding(paddingValues),
                heroes = allHeroes,
                navController = navController
            )
        }
    )
}