/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 10:52 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.actions

import com.badlogic.gdx.InputAdapter
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.level.TurretKind

class InputHandler(private val screen: GameScreen) : InputAdapter() {

    var currentKind: TurretKind? = null

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        screen.placeTurret(screenX, screenY, currentKind ?: return false)
        currentKind = null
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        screen.map.updatePlacementPreview(screenX, screenY, currentKind)
        screen.placeTurret(screenX, screenY, currentKind ?: return false)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        screen.map.updatePlacementPreview(screenX, screenY, currentKind)
        return true
    }
}