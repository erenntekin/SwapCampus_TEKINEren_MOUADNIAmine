package com.example.swapcampus_mamaabbas

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.swapcampus_mamaabbas.composeapp.AppContainer

// The UI tests find nodes by their English text: run them on an emulator set to English.

/** AppContainer is a singleton: a user logged in by a previous test stays logged in. */
fun ComposeTestRule.startLoggedOut() {
    runOnUiThread { AppContainer.authRepository.logout() }
    waitForIdle()
}

/** Fills the login form shown on the screen and taps the button. */
fun ComposeTestRule.fillLoginForm(email: String = "alice@isep.fr", password: String = "1234") {
    onNodeWithText("Email").performTextInput(email)
    onNodeWithText("Password").performTextInput(password)
    // On the "Log in" tab, the text "Log in" is both the tab and the button: we use a test tag
    onNodeWithTag("login_button").performClick()
}

/** Logs in through the "Log in" tab, then comes back to the list of listings. */
fun ComposeTestRule.logInAsAlice() {
    onNodeWithText("Log in").performClick() // bottom bar tab
    fillLoginForm()
    onNodeWithText("Listings").performClick() // after login the app opens the Profile tab
}
