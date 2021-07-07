/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 8:14 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.resources.Assets

class PlacementPreview : Image(TextureRegion(Assets.tex("entity/turret/turret_base"))) {

    private val shapeRenderer = ShapeRenderer()
    private var radius = 0f

    init {
        setSize(1f, 1f)
        setColor(1f, 1f, 1f, 0.5f)
        setOrigin(Align.center)
        shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.4f)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        // Batch needs to disabled temporarily for the shape renderer to do its work
        batch.end()
        Gdx.gl20.glEnable(GL20.GL_BLEND)
        shapeRenderer.projectionMatrix = batch.projectionMatrix
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.circle(x + 0.5f, y + 0.5f, radius, 64)
        shapeRenderer.end()
        batch.begin()
        super.draw(batch, parentAlpha)
    }

    fun update(position: Vector2, kind: TurretKind) {
        setPosition(position.x - 0.5f, position.y - 0.5f)
        drawable = TextureRegionDrawable(TextureRegion(kind.baseTex))
        radius = kind.range
    }
}