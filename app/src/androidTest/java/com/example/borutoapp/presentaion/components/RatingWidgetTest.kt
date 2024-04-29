package com.example.borutoapp.presentaion.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import com.example.borutoapp.presentation.components.RatingWidget
import com.example.borutoapp.ui.theme.SMALL_PADDING
import org.junit.Rule
import org.junit.Test

class RatingWidgetTest {

    @get: Rule
    val composeTestRule = createComposeRule()
    @Test
    fun passZeroPointZeroValue_Assert_FiveEmptyStars() {
        val rating = 0.0
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(0)
    }
    @Test
    fun passZeroPointSixValue_Assert_FourEmptyStarsOneFilledStar() {
        val rating = 0.6
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(1)
    }
    @Test
    fun passFourPointFiveValue_Assert_ZeroEmptyStarsOneHalfFilledStart4FullStar() {
        val rating = 4.5
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(4)
    }
    @Test
    fun passFourPointThreeValue_Assert_ZeroEmptyStarThreeHalfStarFourFilledStar() {
        val rating = 4.3
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(4)
    }
    @Test
    fun passNegativeValue_Assert_FiveEmptyStar() {
        val rating = -1.0
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(0)
    }
    @Test
    fun passInvalid_Assert_FiveEmptyStar() {
        val rating = 5.2
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = rating
            )
        }
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStar")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("FilledStar")
            .assertCountEquals(0)
    }

}