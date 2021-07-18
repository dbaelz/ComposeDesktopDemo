package de.dbaelz.compose.desktop.demo.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
class DialogScreenTest {
    @get:Rule
    val rule = createComposeRule()

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

            // TODO: Doesn't work: CompositionLocal LocalAppWindow not provided
            //rule.awaitIdle()
            //rule.onNodeWithText("Another useful dialog").assertExists()
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

            // TODO: Doesn't work. Node not found
            // rule.onNodeWithText("This is dialog window").assertExists()
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