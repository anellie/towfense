/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:46 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.editor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.kotcrab.vis.ui.util.ToastManager
import com.kotcrab.vis.ui.widget.VisTable
import xyz.angm.towfense.Towfense
import xyz.angm.towfense.graphics.screens.MenuScreen
import xyz.angm.towfense.graphics.screens.viewport

class EditorScreen : ScreenAdapter() {

    private var mapIdx = 0
    var map = EditorMap.of(0)
    private val input = EditorInputHandler(this)

    var gameStage = Stage(FitViewport(map.path.mapSize.x.toFloat(), map.path.mapSize.y.toFloat()))
    private val uiStage = Stage(viewport)
    private val toasts = ToastManager(uiStage)

    override fun show() {
        gameStage.addActor(map)
        uiStage.addActor(EditorWindow(this))
        toasts.alignment = Align.bottomRight

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(uiStage)
        inputMultiplexer.addProcessor(input)
        Gdx.input.inputProcessor = inputMultiplexer
        Gdx.input.isCursorCatched = false
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        gameStage.viewport.apply()
        gameStage.act(delta)
        gameStage.draw()

        uiStage.viewport.apply()
        uiStage.act(delta)
        uiStage.draw()
    }

    override fun resize(width: Int, height: Int) {
        uiStage.viewport.update(width, height, true)
        gameStage.viewport.update(width, height, true)
    }

    internal fun toast(msg: String) {
        val table = VisTable()
        table.add(msg).pad(10f)
        toasts.show(table, 3f)
    }

    internal fun changeLevel(mapIdx: Int) {
        this.mapIdx = mapIdx
        reload()
    }

    fun returnToMenu() {
        (Gdx.app.applicationListener as Towfense).screen = MenuScreen(Gdx.app.applicationListener as Towfense)
    }

    internal fun reload() {
        map.remove()
        map = EditorMap.of(mapIdx)
        gameStage = Stage(FitViewport(map.path.mapSize.x.toFloat(), map.path.mapSize.y.toFloat()))
        gameStage.addActor(map)
    }
}