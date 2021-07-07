/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/8/21, 12:00 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.badlogic.gdx.Gdx
import com.kotcrab.vis.ui.widget.VisLabel
import xyz.angm.towfense.graphics.screens.GameScreen

class DebugWindow(private val screen: GameScreen) : Window("Debug", false) {

    private val fpsLabel = VisLabel("---")
    private val entityLabel = VisLabel("---")

    init {
        add(VisLabel("FPS: ")).pad(5f)
        add(fpsLabel).pad(5f).row()
        add(VisLabel("Entities loaded: ")).pad(5f)
        add(entityLabel).pad(5f)
        setSize(300f, 300f)
        setPosition(999999f, 999999f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        fpsLabel.setText(Gdx.graphics.framesPerSecond.toString())
        entityLabel.setText(screen.entitiesLoaded.toString())
    }
}