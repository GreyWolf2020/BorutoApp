package com.example.borutoapp.presentaion.screens.search


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.borutoapp.presentation.screens.search.SearchWidget
import org.junit.Rule
import org.junit.Test

class SearchWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        val text = mutableStateOf("")
        val searchText = "Stevdza-San"
        composeTestRule.setContent {
            SearchWidget(
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                onSearchClicked = {},
                onClosedClicked = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("TextField")
            .performTextInput(searchText)
        composeTestRule.onNodeWithContentDescription("TextField")
            .assertTextEquals(searchText)
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButton_assertEmptyInputText() {
        val text = mutableStateOf("")
        val searchText = "Stevdza-San"
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchWidget(
                    text = text.value,
                    onTextChange = {
                        text.value = it
                    },
                    onSearchClicked = {},
                    onClosedClicked = {
                        searchWidgetShown.value = false
                    }
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("TextField")
            .performTextInput(searchText)
        composeTestRule.onNodeWithContentDescription("CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription("CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription("SearchWidget")
            .assertDoesNotExist()
    }

    @Test
    fun openSearchWidget_pressCloseButton_assertSearchWidgetNotVisible() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchWidget(
                    text = text.value,
                    onTextChange = {
                        text.value = it
                    },
                    onSearchClicked = {},
                    onClosedClicked = {
                        searchWidgetShown.value = false
                    }
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription("SearchWidget")
            .assertDoesNotExist()
    }

}