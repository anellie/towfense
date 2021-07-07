/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/8/21, 12:11 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.actions

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.*
import xyz.angm.towfense.graphics.panels.game.PausePanel
import xyz.angm.towfense.graphics.screens.GameScreen

/** An action represents a function to be executed when the player presses a key.
 * @property type The internal name for an action, ex. 'walkLeft'
 * @property keyDown Function to be executed when the key is pressed down
 * @property keyUp Function to be executed when the key is released, can be null (which will be an empty function) */
data class PlayerAction(
    val type: String,
    val keyDown: (GameScreen) -> Unit,
    val keyUp: (GameScreen) -> Unit
)

/** The object that contains all actions and allows retrieving them. */
object PlayerActions {

    val actions = ObjectMap<String, PlayerAction>()

    init {
        fun add(name: String, down: (GameScreen) -> Unit, up: (GameScreen) -> Unit) {
            actions[name] = PlayerAction(name, down, up)
        }

        fun add(name: String, down: (GameScreen) -> Unit) = add(name, down, {})

        add("pause") { it.pushPanel(PausePanel(it)) }
    }

    /** Get an action. */
    operator fun get(type: String): PlayerAction? = actions[type]
}
