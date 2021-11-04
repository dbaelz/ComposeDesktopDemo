package de.dbaelz.compose.desktop.demo.view

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class DialogScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `Back navigation works`() {
        runBlocking(Dispatchers.Main) {
            rule.setContent {
                DialogScreen { }
            }

            rule.onNodeWithContentDescription("navigate back").assertExists()
            rule.onNodeWithContentDescription("navigate back").assertHasClickAction()
        }
    }

    @Test
    fun `AlertDialog shown on click`() {
        val dialogText = "Alert Dialog"

        runBlocking(Dispatchers.Main) {
            rule.setContent {
                DialogScreen { }
            }

            rule.onNodeWithText(dialogText).assertExists()
            rule.onNodeWithText(dialogText).assertHasClickAction()

            rule.awaitIdle()

            rule.onNodeWithText(dialogText).performClick()

            // Only works with PopupAlertDialogProvider
            // See: https://kotlinlang.slack.com/archives/C01D6HTPATV/p1628686951262700
            rule.awaitIdle()
            rule.onNodeWithText("Another useful dialog").assertExists()
        }
    }

    @Test
    fun `Dialog Window shown on click`() {
        val dialogText = "Dialog Window"

        runBlocking(Dispatchers.Main) {
            rule.setContent {
                DialogScreen { }
            }

            rule.onNodeWithText(dialogText).assertExists()
            rule.onNodeWithText(dialogText).assertHasClickAction()

            rule.awaitIdle()

            rule.onNodeWithText(dialogText).performClick()

            // Doesn't work because the text in the dialog is a separate window.
            // See https://kotlinlang.slack.com/archives/C01D6HTPATV/p1628686951262700
            //rule.onNodeWithText("This is dialog window").assertExists()
        }
    }

    @Test
    fun `Popup shown on click`() {
        val dialogText = "Popup"
        val popupText = "Hello Popup"

        runBlocking(Dispatchers.Main) {
            rule.setContent {
                DialogScreen { }
            }

            rule.onNodeWithText(dialogText).assertExists()
            rule.onNodeWithText(dialogText).assertHasClickAction()
            rule.onNodeWithText(popupText).assertDoesNotExist()

            rule.awaitIdle()


            rule.onNodeWithText(dialogText).performClick()

            rule.awaitIdle()

            rule.onNodeWithText(popupText).assertExists()
        }
    }
}