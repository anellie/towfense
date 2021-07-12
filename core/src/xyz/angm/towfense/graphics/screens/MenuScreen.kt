/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 4:53 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import xyz.angm.towfense.Towfense
import xyz.angm.towfense.editor.EditorScreen
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.panels.PanelStack
import xyz.angm.towfense.graphics.panels.menu.LoadingPanel
import xyz.angm.towfense.graphics.panels.menu.MainMenuPanel
import xyz.angm.towfense.level.WorldMap
import xyz.angm.towfense.resources.Assets

/** The menu screen. It manages the current menu panel stack and draws it on top of a nice background.
 * @param game The game instance. */
class MenuScreen(private val game: Towfense) : ScreenAdapter(), Screen {

    private val stage = Stage(viewport)
    private var panelStack = PanelStack()

    override fun show() {
        stage.addActor(panelStack)

        Assets.init()
        panelStack.pushPanel(LoadingPanel(this))
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    /** Called when [Assets] has finished loading. Will remove the loading screen
     * and show the main menu. */
    fun doneLoading() {
        panelStack.popPanel(-1)
        panelStack.pushPanel(MainMenuPanel(this))
    }

    override fun pushPanel(panel: Panel) = panelStack.pushPanel(panel)

    override fun popPanel() {
        if (panelStack.panelsInStack > 1) panelStack.popPanel()
    }

    override fun resize(width: Int, height: Int) = stage.viewport.update(width, height, true)

    override fun dispose() {
        stage.dispose()
        panelStack.dispose()
    }

    fun startNewGame(levelIdx: Int) {
        game.screen = GameScreen(game, WorldMap.of(levelIdx))
    }

    fun editor() {
        game.screen = EditorScreen()
    }
}
