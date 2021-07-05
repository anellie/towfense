/*
 * Developed as part of the towfense project.
 * This file was last modified at 12/13/20, 9:17 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.PerformanceCounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import xyz.angm.rox.Engine
import xyz.angm.towfense.Towfense
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.panels.PanelStack
import xyz.angm.towfense.runLogE

/** The game screen. Active during gameplay.
 *
 * This screen is mainly a bag of other objects that make up game state;
 * and should not have any other responsibility other than initializing them and
 * setting up their interactions that drive the game.
 * The only other responsibility of this class is putting together all graphics sources and drawing them.
 *
 * @property engine The ECS engine used */
class GameScreen(private val game: Towfense) : ScreenAdapter(), Screen {

    private val coScope = CoroutineScope(Dispatchers.Default)
    val bench = PerformanceCounter("render")

    // Entities
    val engine = Engine()

    // 2D Graphics
    private val stage = Stage(viewport)
    private val uiPanels = PanelStack()

    val entitiesLoaded get() = engine.entities.size
    val systemsActive get() = engine.systems.size

    init {
        initSystems()
        initState()
        initRender()
    }

    override fun render(delta: Float) {
        runLogE("Client", "rendering") { renderInternal(delta) }
    }

    private fun renderInternal(delta: Float) {
        // Uncomment this and the stop call at the end to enable performance profiling.
        // startBench(delta)

        engine.update(delta)
        stage.act()
        stage.draw()

        // bench.stop()
    }

    @Suppress("unused")
    private fun startBench(delta: Float) {
        bench.tick(delta)
        bench.start()
    }

    override fun pushPanel(panel: Panel) {
        uiPanels.pushPanel(panel)
    }

    override fun popPanel() {
        uiPanels.popPanel()
    }

    // Initialize all ECS systems
    private fun initSystems() = engine.apply {
        val screen = this@GameScreen
    }

    // Initialize everything not render-related
    private fun initState() {
        // Input
        Gdx.input.inputProcessor = stage
        Gdx.input.isCursorCatched = false
        Gdx.input.setCursorPosition(stage.viewport.screenWidth / 2, (stage.viewport.screenY + stage.viewport.screenHeight) / 2)
    }

    // Initialize all rendering components
    private fun initRender() {
        // 2D / Stage
        stage.addActor(uiPanels)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    /** hide is called when the screen is no longer active, at which point this type of screen becomes dereferenced and needs to be disposed. */
    override fun hide() = dispose()

    override fun dispose() {
        coScope.cancel()
        uiPanels.dispose()
    }
}