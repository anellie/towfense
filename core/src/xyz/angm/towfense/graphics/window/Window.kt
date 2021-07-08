/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 12:47 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.kotcrab.vis.ui.widget.VisWindow
import xyz.angm.towfense.resources.I18N

abstract class Window(name: String, closable: Boolean) : VisWindow(I18N.tryGet(name) ?: name, true) {
    init {
        if (closable) addCloseButton()
    }
}