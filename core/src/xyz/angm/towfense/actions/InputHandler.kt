/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:51 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.actions

import com.badlogic.gdx.InputAdapter
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.level.TurretKind
import xyz.angm.towfense.resources.configuration

class InputHandler(private val screen: GameScreen) : InputAdapter() {

    var currentKind: TurretKind? = null
    var placeMultiple = false

    override fun keyDown(keycode: Int): Boolean {
        val bind = configuration.keybinds[keycode] ?: return false
        bind.keyDown(screen)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        val bind = configuration.keybinds[keycode] ?: return false
        bind.keyUp(screen)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val success = screen.placeTurret(screenX, screenY, currentKind ?: return false)
        if (success && !(placeMultiple && screen.coins >= currentKind!!.cost)) currentKind = null
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        screen.map.updatePlacementPreview(screenX, screenY, currentKind)
        return true
    }
}