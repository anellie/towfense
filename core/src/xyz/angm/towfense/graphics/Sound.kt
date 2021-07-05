/*
 * Developed as part of the towfense project.
 * This file was last modified at 11/29/20, 3:20 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import xyz.angm.towfense.resources.Assets

/** A function that will make the given table make a clicking noise when
 * clicked by the user. Used for buttons in the menu and similar. */
fun Table.click() {
    addCaptureListener {
        if ((it as? InputEvent)?.type == InputEvent.Type.touchDown)
            Assets.sound("ui/click").play()
        false
    }
}