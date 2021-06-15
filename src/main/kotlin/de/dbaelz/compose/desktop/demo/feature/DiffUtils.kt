package de.dbaelz.compose.desktop.demo.feature

import com.github.difflib.DiffUtils
import com.github.difflib.patch.Patch

class DiffUtils() {
    fun delta(fileLeft: List<String>, fileRight: List<String>) {
        val patch: Patch<String> = DiffUtils.diff(fileLeft, fileRight)

        patch.deltas.forEach {
            println(it)
            // TODO: Do something with the delta
        }
    }
}