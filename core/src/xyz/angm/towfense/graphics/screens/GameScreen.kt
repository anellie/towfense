/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 2:44 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.PerformanceCounter
import com.badlogic.gdx.utils.viewport.FitViewport
import xyz.angm.rox.Engine
import xyz.angm.rox.EntityListener
import xyz.angm.rox.systems.EntitySystem
import xyz.angm.towfense.Towfense
import xyz.angm.towfense.ecs.createEnemy
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.systems.DisplaySystem
import xyz.angm.towfense.ecs.systems.PathMovementSystem
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.panels.PanelStack
import xyz.angm.towfense.level.WorldMap
import xyz.angm.towfense.runLogE

/** The game screen. Active during gameplay.
 *
 * This screen is mainly a bag of other objects that make up game state;
 * and should not have any other responsibility other than initializing them and
 * setting up their interactions that drive the game.
 * The only other responsibility of this class is putting together all graphics sources and drawing them.
 *
 * @property engine The ECS engine used */
class GameScreen(private val game: Towfense, private val map: WorldMap = WorldMap.of(0)) : ScreenAdapter(), Screen {

    private val bench = PerformanceCounter("render")

    // Entities
    private val engine = Engine()

    // 2D Graphics
    private val uiStage = Stage(viewport)
    private val uiPanels = PanelStack()
    private val gameStage = Stage(FitViewport(map.path.mapSize.x.toFloat(), map.path.mapSize.y.toFloat()))

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

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        engine.update(delta)

        gameStage.act(delta)
        gameStage.draw()

        uiStage.act(delta)
        uiStage.draw()

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
        position // initialize globals...

        add(PathMovementSystem(map.path))

        val display = DisplaySystem(gameStage)
        add(display as EntityListener)
        add(display as EntitySystem)
    }

    // Initialize everything not render-related
    private fun initState() {
        // Input
        Gdx.input.inputProcessor = uiStage
        Gdx.input.isCursorCatched = false
        Gdx.input.setCursorPosition(uiStage.viewport.screenWidth / 2, (uiStage.viewport.screenY + uiStage.viewport.screenHeight) / 2)

        // test enemy
        createEnemy(engine)
    }

    // Initialize all rendering components
    private fun initRender() {
        // 2D / Stage
        uiStage.addActor(uiPanels)

        map.drawBackground()
        gameStage.addActor(map)
    }

    override fun resize(width: Int, height: Int) {
        uiStage.viewport.update(width, height, true)
        gameStage.viewport.update(width, height, true)
    }

    /** hide is called when the screen is no longer active, at which point this type of screen becomes dereferenced and needs to be disposed. */
    override fun hide() = dispose()

    override fun dispose() {
        uiPanels.dispose()
    }
}