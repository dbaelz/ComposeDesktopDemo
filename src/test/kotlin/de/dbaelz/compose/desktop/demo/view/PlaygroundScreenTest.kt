package de.dbaelz.compose.desktop.demo.view

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class PlaygroundScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `Back navigation works`() {
        runBlocking(Dispatchers.Main) {
            rule.setContent {
                PlaygroundScreen { }
            }

            rule.onNodeWithContentDescription("navigate back").assertExists()
            rule.onNodeWithContentDescription("navigate back").assertHasClickAction()
        }
    }

    @Test
    fun `PlaygroundScreen with back navigation and dummy text is shown`() {
        runBlocking(Dispatchers.Main) {
            rule.setContent {
                val testBackNavigation = {}
                PlaygroundScreen(testBackNavigation)
            }

            rule.onNodeWithContentDescription("navigate back").assertExists()
            rule.onNodeWithContentDescription("navigate back").assertHasClickAction()

            rule.onNodeWithText("Playground").assertExists()
        }
    }
}