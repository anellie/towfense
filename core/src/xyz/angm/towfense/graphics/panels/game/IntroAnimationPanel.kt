/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 2:10 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.game

import com.kotcrab.vis.ui.widget.VisLabel
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.GameScreen

class IntroAnimationPanel(screen: GameScreen, text: String) : Panel(screen) {

    init {
        background = skin.getDrawable("transparent")
        add(VisLabel(text, "default-48pt"))
    }
}