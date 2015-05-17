package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.extension.NamedObjectFactoryBase
import groovy.transform.Canonical

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Canonical
class TargetFactory extends NamedObjectFactoryBase<TargetExt> {
  Class klass = TargetExt

  def addTasksFor( TargetExt x ) {
    project.with {
      assert state.executed
      def createTask = task( "targetCreate${x.name.capitalize()}", type: CreateTargetTask ) {
        ext = x
      }

      def addFilesTask = task( "target${x.name.capitalize()}AddFiles", type: TargetAddFilesTask ) {
        ext = x
      }

      addFilesTask.dependsOn createTask

      tasks[ "targets" ].dependsOn addFilesTask
    }
  }
}
