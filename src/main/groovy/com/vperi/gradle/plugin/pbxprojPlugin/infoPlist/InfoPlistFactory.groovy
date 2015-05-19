package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.vperi.gradle.extension.NamedObjectFactoryBase
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetExt
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Canonical
@Slf4j
class InfoPlistFactory extends NamedObjectFactoryBase<InfoPlistExt> {
  Class klass = InfoPlistExt
  TargetExt target

  @Override
  def createInstance( String name ) {
    new InfoPlistExt( name, project, target )
  }

  def addTasksFor( InfoPlistExt x ) {
    project.with {
      def n = target.name.capitalize(  )
      def t = task( "target${n}CreateInfoPlist", type: CreateInfoPlistTask ) {
        ext = x
      }
      def addFilesTask = tasks.findByPath( "target${n}AddFiles" )
      if ( addFilesTask ) {
        addFilesTask.dependsOn t
      }
    }
  }
}
