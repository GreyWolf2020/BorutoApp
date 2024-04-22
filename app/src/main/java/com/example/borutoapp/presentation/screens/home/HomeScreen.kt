package com.example.borutoapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.ui.theme.statusBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.statusBar)
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