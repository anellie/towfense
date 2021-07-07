/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 2:19 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.components

import xyz.angm.rox.Component

class PathedComponent : Component {
    var segment = 0
    var distTravelled = 0f
}