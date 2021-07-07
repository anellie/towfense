/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 7:01 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.kotcrab.vis.ui.widget.VisWindow

abstract class Window(name: String, closable: Boolean) : VisWindow(name, true) {
    init {
        if (closable) addCloseButton()
    }
}