/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 5:51 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.IntVector
import xyz.angm.towfense.resources.Assets

class PlacementPreview : Image(TextureRegion(Assets.tex("entity/turret/turret_base"))) {

    private val shapeRenderer = ShapeRenderer()
    private var radius = 0f

    init {
        setSize(1f, 1f)
        setColor(1f, 1f, 1f, 0.5f)
        setOrigin(Align.center)
        isVisible = false
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

    fun update(position: IntVector, kind: TurretKind, free: Boolean) {
        setPosition(position.x.toFloat(), position.y.toFloat())
        drawable = TextureRegionDrawable(TextureRegion(kind.baseTex))
        radius = kind.range
        color = if (free) Color.WHITE else Color.RED
        shapeRenderer.setColor(color.r * 0.1f, color.g * 0.1f, color.b * 0.1f, 0.4f)
    }
}