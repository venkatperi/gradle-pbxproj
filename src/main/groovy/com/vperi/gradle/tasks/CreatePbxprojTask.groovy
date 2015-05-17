package com.vperi.gradle.tasks

import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetExt
import org.gradle.api.tasks.TaskAction

/**
 * CreateTargetTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
class CreatePbxprojTask extends XcodeProjTaskBase<TargetExt> {

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  def exec() {
    if ( !xproj.projectDir.exists() ) {
      xproj.createProject()
    }
  }
}
