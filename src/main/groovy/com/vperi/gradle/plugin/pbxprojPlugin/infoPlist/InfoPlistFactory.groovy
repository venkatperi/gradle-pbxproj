package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

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
class InfoPlistFactory extends NamedObjectFactoryBase<InfoPlistExt> {
  Class klass = InfoPlistExt

  def addTasksFor( InfoPlistExt x ) {
    project.with {

      def createTask = task( "createInfoPlist", type: CreateInfoPlistTask ) {
        ext = x
      }

      tasks[ "pbxproj" ].dependsOn createTask
    }
  }
}
