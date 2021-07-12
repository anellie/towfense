/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 5:52 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.resources

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import ktx.assets.file
import xyz.angm.towfense.graphics.Skin

object Assets {
    private val assets = AssetManager()

    fun init() {
        if (assets.loadedAssets > 0) return // already initialized

        load<Texture>("textures/ui/logo.png")
        assets.finishLoading() // Assets queued are initial assets, load immediately
        Skin.reload()

        loadList<Sound>(file("sounds.list"))
        loadList<Texture>(file("textures.list"))
    }

    fun tex(name: String): Texture = get("textures/$name.png")

    fun sound(name: String): Sound = get("sounds/$name.ogg")

    fun <T> get(file: String): T = assets.get(file)

    private inline fun <reified T : Any> loadList(list: FileHandle) {
        for (line in list.readString().lines()) {
            load<T>(line)
        }
    }

    private inline fun <reified T : Any> load(file: String) = assets.load(file, T::class.java)

    /** Continues loading game assets. Returns loading progress as a float with value range 0-1. 1 means loading is finished.
     * @param processingTime How long to process, in milliseconds. */
    fun continueLoading(processingTime: Int = 10): Float {
        val time = System.currentTimeMillis()
        while ((System.currentTimeMillis() - time) < processingTime)
        assets.update()
        return assets.progress
    }
}