/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/8/21, 1:30 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.editor

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import xyz.angm.towfense.IntVector

class EditorInputHandler(private val screen: EditorScreen) : InputAdapter() {

    private val tmpV = Vector2()
    private val tmpIV = IntVector()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        when (button) {
            Input.Buttons.LEFT -> screen.map.materializeLast()
            Input.Buttons.RIGHT -> screen.map.removeLast()
            Input.Buttons.MIDDLE -> {
                tmpV.set(screenX.toFloat(), screenY.toFloat())
                screen.gameStage.screenToStageCoordinates(tmpV)
                screen.map.path.start.set(tmpV)
                screen.reload()
            }
        }
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        tmpV.set(screenX.toFloat(), screenY.toFloat())
        screen.gameStage.screenToStageCoordinates(tmpV)

        val pos = tmpIV.set(tmpV)
        val diff = pos.sub(screen.map.endOfSecondLast())

        val dir: Int
        val len: Int
        when {
            diff.x > 0 && diff.y == 0 -> {
                len = diff.x
                dir = 3
            }
            diff.x < 0 && diff.y == 0 -> {
                len = -diff.x
                dir = 1
            }
            diff.y > 0 && diff.x == 0 -> {
                len = diff.y
                dir = 0
            }
            diff.y < 0 && diff.x == 0 -> {
                len = -diff.y
                dir = 2
            }
            else -> return false
        }

        screen.map.removeTempSegment()
        screen.map.setTempSegment(intArrayOf(dir, len))
        return true
    }
}