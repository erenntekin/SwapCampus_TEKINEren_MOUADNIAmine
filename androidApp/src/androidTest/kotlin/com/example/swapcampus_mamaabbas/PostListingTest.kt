package com.example.swapcampus_mamaabbas

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Compose UI tests of the "post a listing" flow (Labwork 2, scenarios 4 and 5).
 * Run: ./gradlew :androidApp:connectedAndroidTest (emulator in English).
 */
@RunWith(AndroidJUnit4::class)
class PostListingTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() = composeRule.startLoggedOut()

    @Test
    fun postListing_whileLoggedOut_asksLoginThenPublishes() {
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).performClick() // the FAB text is only in the unmerged tree
        composeRule.onNodeWithText("Email").performTextInput("alice@isep.fr")
        composeRule.onNodeWithText("Password").performTextInput("1234")
        composeRule.onNodeWithTag("login_button").performClick()
        composeRule.onNodeWithText("Title").performTextInput("Calculator TI-84")
        composeRule.onNodeWithText("Price").performTextInput("30")
        composeRule.onNodeWithText("Publish").performScrollTo().performClick()
        // A LazyColumn only composes visible cards: the search makes sure the new card is on screen
        composeRule.onNodeWithText("Search a textbook, a bike…").performTextInput("TI-84")
        composeRule.onNodeWithText("Calculator TI-84").assertIsDisplayed()
    }

    // Exercise 3 (solution)
    @Test
    fun publish_withEmptyTitle_showsError() {
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).performClick()
        composeRule.fillLoginForm()
        composeRule.onNodeWithText("Price").performTextInput("10")
        composeRule.onNodeWithText("Publish").performScrollTo().performClick()
        composeRule.onNodeWithText("Give your listing a title.").assertIsDisplayed()
    }
}
