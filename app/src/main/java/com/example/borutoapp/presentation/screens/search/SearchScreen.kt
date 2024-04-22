package com.example.borutoapp.presentation.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.ui.theme.statusBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.statusBar)
    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(it)
                },
                onClosedClicked = {
                    navController.popBackStack()
                },
                onSearchClicked = {
                    searchViewModel.searchHeroes(query = it)
                }
            )
        },
        content = { paddingValue->
            val modifier = Modifier.padding(top = paddingValue.calculateTopPadding())
            ListContent(heroes = heroes, navController = navController)
        }
    )
}