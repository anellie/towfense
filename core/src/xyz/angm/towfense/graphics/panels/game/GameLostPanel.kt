/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:23 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.game

import ktx.actors.onClick
import ktx.actors.plusAssign

import ktx.scene2d.scene2d
import ktx.scene2d.vis.visLabel
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.resources.I18N

class GameLostPanel(screen: GameScreen) : Panel(screen) {

    init {
        background = skin.getDrawable("red-transparent")

        this += scene2d.visTable {
            visLabel(I18N["lost-message"], style = "default-48pt") { it.pad(10f).row() }
            visLabel(I18N["lost-message-sub"], style = "default-24pt") { it.pad(10f).padBottom(50f).row() }

            visTextButton(I18N["pause.exit"]) {
                onClick { screen.returnToMainMenu() }
            }

            setFillParent(true)
        }
    }
}