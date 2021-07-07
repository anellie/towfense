/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/8/21, 12:11 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.game

import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.graphics.Skin
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.resources.I18N

class PausePanel(screen: GameScreen) : Panel(screen) {

    init {
        clearChildren()
        this += scene2d.visTable {
            pad(0f, 0f, 100f, 0f)

            visTextButton(I18N["pause.resume"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { screen.popPanel() }
            }
            visTextButton(I18N["pause.exit"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { screen.returnToMainMenu() }
            }

            setFillParent(true)
        }
    }
}