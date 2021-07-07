/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:40 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs

import xyz.angm.rox.mapperFor
import xyz.angm.towfense.ecs.components.*

val position = mapperFor<PositionComponent>()
val velocity = mapperFor<VelocityComponent>()

val display = mapperFor<DisplayComponent>()
val pathed = mapperFor<PathedComponent>()

val turret = mapperFor<TurretComponent>()
val enemy = mapperFor<EnemyComponent>()
val bullet = mapperFor<BulletComponent>()