/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 4:24 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.editor

import com.badlogic.gdx.Gdx
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel
import ktx.actors.onChange
import ktx.actors.onClick
import ktx.scene2d.horizontalGroup
import ktx.scene2d.label
import ktx.scene2d.scene2d
import ktx.scene2d.vis.spinner
import ktx.scene2d.vis.visTable
import ktx.scene2d.vis.visTextButton
import xyz.angm.towfense.graphics.Skin
import xyz.angm.towfense.graphics.window.Window
import xyz.angm.towfense.level.Levels

class EditorWindow(screen: EditorScreen) : Window("Editor", false) {

    init {
        add(scene2d.visTable {
            val lModel = IntSpinnerModel(0, 1, Levels.size)
            val xModel = IntSpinnerModel(screen.map.path.mapSize.x, 1, 128)
            val yModel = IntSpinnerModel(screen.map.path.mapSize.y, 1, 128)

            horizontalGroup {
                label("Select Level    ")
                spinner("", lModel) {
                    onChange {
                        screen.changeLevel(lModel.value - 1)
                        xModel.setValue(screen.map.path.mapSize.x, false)
                        yModel.setValue(screen.map.path.mapSize.y, false)

                        screen.toast("Changed to level ${lModel.value - 1}!")
                    }
                }
                it.pad(5f).padTop(20f).row()
            }

            horizontalGroup {
                label("Map X size    ")
                spinner("", xModel) {
                    onChange {
                        screen.map.path.mapSize.x = xModel.value
                        screen.reload()
                    }
                }
                it.pad(5f).row()
            }
            horizontalGroup {
                label("Map Y size    ")
                spinner("", yModel) {
                    onChange {
                        screen.map.path.mapSize.y = yModel.value
                        screen.reload()
                    }
                }
                it.pad(5f).row()
            }

            visTextButton("Create New Level", "vis-default") {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(5f).row()
                onClick {
                    screen.toast("Created level ${Levels.new() + 1}!")
                    lModel.max = Levels.size - 1
                }
            }
            visTextButton("Save", "vis-default") {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(5f).row()
                onClick {
                    Levels.save()
                    screen.toast("Saved!")
                }
            }
            visTextButton("Exit", "vis-default") {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(5f).row()
                onClick { Gdx.app.exit() }
            }

            setFillParent(true)
        })
        pack()
    }
}