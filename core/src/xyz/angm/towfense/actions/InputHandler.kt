/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:16 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.actions

import com.badlogic.gdx.InputAdapter
import xyz.angm.towfense.graphics.screens.GameScreen

class InputHandler(private val screen: GameScreen) : InputAdapter() {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        screen.placeTurret(screenX, screenY)
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        screen.map.updatePlacementPreview(screenX, screenY)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        screen.map.updatePlacementPreview(screenX, screenY)
        return true
    }
}