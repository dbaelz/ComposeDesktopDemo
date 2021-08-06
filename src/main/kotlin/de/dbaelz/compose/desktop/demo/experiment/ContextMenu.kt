package de.dbaelz.compose.desktop.demo.experiment

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ContextMenuItem

/**
 * Status: ContextMenu doesn't work (alpha3)
 * See https://kotlinlang.slack.com/archives/C01D6HTPATV/p1628276982193400
 */
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ExperimentContextMenu() {
    ContextMenuDataProvider(
        items = {
            listOf(
                ContextMenuItem("Item 1") {},
                ContextMenuItem("Item 2") {}
            )
        }
    ) {
        Box {
            Text("No menu on right click")
        }

        Text("No menu on right click")

        TextField("Menu with default entries shown", {})
    }
}