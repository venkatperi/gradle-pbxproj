package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.tasks.XcodeProjTaskBase
import org.gradle.api.tasks.TaskAction

/**
 * CreateTargetTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class CreateTargetTask extends XcodeProjTaskBase<TargetExt> {

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  def exec() {
    ext.with {
      xproj.createTarget name, type, platform, language, deploymentTarget
    }
  }
}