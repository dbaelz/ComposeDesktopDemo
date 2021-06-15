package de.dbaelz.compose.desktop.demo.difftool

import androidx.compose.desktop.AppWindow
import com.github.difflib.DiffUtils
import com.github.difflib.patch.Patch

fun selectAndReadFile(localAppWindow: AppWindow): List<String>? {
    val fileDialog = java.awt.FileDialog(localAppWindow.window)
    fileDialog.isVisible = true

    val selectedFile = fileDialog.files.firstOrNull()
    if (selectedFile != null) {
        return selectedFile.readLines()
    }

    return null
}

fun differ(fileLeft: List<String>, fileRight: List<String>) {
    val patch: Patch<String> = DiffUtils.diff(fileLeft, fileRight)

    patch.deltas.forEach {
        println(it)
        // TODO: Do something with the delta
    }
}