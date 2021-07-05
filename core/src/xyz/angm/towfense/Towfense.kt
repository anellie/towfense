/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/5/21, 9:09 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense

import com.badlogic.gdx.Game
import com.kotcrab.vis.ui.VisUI
import xyz.angm.towfense.graphics.screens.MenuScreen
import kotlin.system.exitProcess

/** The game itself. Only sets the screen, everything else is handled per-screen. */
class Towfense : Game() {

    /** Called when libGDX environment is ready. */
    override fun create() {
        VisUI.load()
        setScreen(MenuScreen(this))
    }

    override fun dispose() = exitProcess(0)
}
