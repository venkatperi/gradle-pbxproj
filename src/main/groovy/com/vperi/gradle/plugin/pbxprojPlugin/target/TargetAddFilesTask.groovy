package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.tasks.XcodeProjTaskBase
import org.gradle.api.tasks.TaskAction

import java.nio.file.Path

/**
 * CreateTargetTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class TargetAddFilesTask extends XcodeProjTaskBase<TargetExt> {

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  def exec() {
    def rootPath = project.rootDir.toPath()
    ext.with {
      def infoPlistFile = "info.plist"
      def configName = "main"
      def config = project.sourceSets."$configName"

      def infoPlist
      Map<String, List<String>> files = [ : ]
      [ "objc", "swift", "resources" ].each {
        config."$it".visit {
          Path path = it.file.toPath()
          def relative = rootPath.relativize( path )
          def group = relative.parent.toString()
          if ( !files.containsKey( group ) ) files[ group ] = [ ]
          files[ group ].add it.file.path
          if ( path.fileName.toString().toLowerCase() == infoPlistFile ) {
            infoPlist = path
          }
        }
      }

      files.each { k, v ->
        xproj.addFilesToTarget name, k, v
      }

      if ( infoPlist ) {
        xproj.buildSetting( name, configuration, "INFOPLIST_FILE", infoPlist.toString() )
      }
    }
  }
}

