/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 4:23 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.menu

import com.kotcrab.vis.ui.widget.VisLabel
import ktx.actors.onClick
import ktx.scene2d.scene2d
import ktx.scene2d.vis.flowGroup
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.MenuScreen
import xyz.angm.towfense.level.Levels
import xyz.angm.towfense.resources.I18N

class LevelSelectPanel(screen: MenuScreen) : Panel(screen) {

    init {
        add(VisLabel(I18N["main.select-level"], "default-48pt")).pad(200f, 0f, 200f, 0f).row()
        add(scene2d.flowGroup {
            spacing = 10f
            for (level in 1..Levels.size) {
                visTextButton(level.toString(), "vis-default") {
                    onClick { screen.startNewGame(level - 1) }
                    pad(30f)
                }
            }
        }).expandY().fillY()
    }
}