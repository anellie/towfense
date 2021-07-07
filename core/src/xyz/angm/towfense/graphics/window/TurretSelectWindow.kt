/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 10:45 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.onClick
import ktx.scene2d.button
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visTextTooltip
import xyz.angm.towfense.graphics.click
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.level.TurretKind

class TurretSelectWindow(screen: GameScreen) : Window("Turrets", false) {

    init {
        val group = ButtonGroup<Button>()
        group.setMaxCheckCount(1)
        group.setMinCheckCount(0)

        for (kind in TurretKind.all()) {
            val btn = scene2d.button {
                add(Image(kind.baseTex))
                setSize(40f, 40f)
                visTextTooltip(kind.name)
                onClick {
                    screen.inputHandler.currentKind = if (screen.inputHandler.currentKind === kind) null else kind
                }
                click()
            }
            group.add(btn)
            add(btn).pad(3f)
        }
    }
}