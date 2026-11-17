package com.example.swapcampus_mamaabbas

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Automator tests: they act on the device itself (rotation, system Back button),
 * which Compose test and Espresso cannot do. Nodes are still found with the Compose rule.
 */
@RunWith(AndroidJUnit4::class)
class DeviceTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
    fun setUp() = composeRule.startLoggedOut()

    @After
    fun restoreRotation() {
        device.setOrientationNatural()
        device.unfreezeRotation()
    }

    @Test
    fun postForm_survivesRotation_andSystemBackClosesIt() {
        composeRule.logInAsAlice()
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).performClick()
        composeRule.onNodeWithText("Title").performTextInput("Desk lamp")

        device.setOrientationLeft() // rotate the real device: the activity is recreated
        device.waitForIdle()
        composeRule.onNodeWithText("Desk lamp").assertIsDisplayed() // rememberSaveable keeps it

        device.setOrientationNatural()
        Espresso.closeSoftKeyboard() // otherwise the first Back only hides the keyboard
        device.pressBack() // system Back, outside our UI
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).assertIsDisplayed()
    }

    // Exercise 4 (solution)
    @Test
    fun detail_systemBack_returnsToList() {
        composeRule.onNodeWithText("Calculatrice TI-83").performClick()
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).assertDoesNotExist() // we are on the detail screen
        device.pressBack()
        composeRule.onNodeWithText("Sell", useUnmergedTree = true).assertIsDisplayed()
    }
}
