/*
 * Developed as part of the towfense project.
 * This file was last modified at 9/19/20, 5:18 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.desktop

import ch.qos.logback.classic.Level
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xyz.angm.towfense.*
import javax.swing.JOptionPane
import kotlin.system.exitProcess

/** The LWJGL configuration used for the game */
val configuration = Lwjgl3ApplicationConfiguration()

/** The game instance */
val game = Towfense()

/** Initialize and launch the game. */
fun main(arg: Array<String>) {
    log.level = if (arg.contains("--debug")) Level.ALL else Level.WARN
    Thread.setDefaultUncaughtExceptionHandler(::handleException)

    setConfiguration()
    Lwjgl3Application(game, configuration)
}

/** Handle exceptions */
private fun handleException(thread: Thread, throwable: Throwable) {
    Gdx.app?.exit()

    log.error { "Whoops. This shouldn't have happened..." }
    log.error { "Exception in thread ${thread.name}:\n" }
    throwable.printStackTrace()
    log.error { "Client is exiting." }

    val builder = StringBuilder()
    builder.append("The game encountered an exception, and is forced to close.\n")
    builder.append("Exception: ${throwable.javaClass.name}: ${throwable.localizedMessage}\n")
    builder.append("For more information, see the console output or log.")

    showDialog(builder.toString(), JOptionPane.ERROR_MESSAGE)
    exitProcess(-1)
}

/** Simple method for showing a dialog. Type should be a type from JOptionPane */
private fun showDialog(text: String, type: Int) = JOptionPane.showMessageDialog(null, text, "towfense", type)

/** Returns the LWJGL configuration. */
private fun setConfiguration() {
    configuration.setIdleFPS(15)
    configuration.useVsync(true)
    configuration.setTitle("towfense")
}