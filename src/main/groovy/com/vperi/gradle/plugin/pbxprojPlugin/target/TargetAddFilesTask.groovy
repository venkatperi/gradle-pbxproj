package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.CreateInfoPlistTask
import com.vperi.gradle.tasks.XcodeProjTaskBase
import com.vperi.xcodeproj.BuildPhase
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
  void exec() {
    def rootPath = project.rootDir.toPath()
    ext.with {
      def infoPlistFile = "info.plist"
      def configName = "main"
      def config = project.sourceSets."$configName"

      def infoPlist = null
      Map<String, List<String>> files = [ : ]
      [ "objc", "swift", "resources" ].each {
        config."$it".visit {
          Path path = it.file.toPath()
          def relative = rootPath.relativize( path )
          def group = relative.parent.toString()
          if ( !files.containsKey( group ) ) files[ group ] = [ ]
          files[ group ].add it.file.path
          if ( path.fileName.toString().toLowerCase() == infoPlistFile ) {
            infoPlist = path.toString()
          }
        }
      }

      def plistTask = project.tasks.findByPath( "createInfoPlist" ) as CreateInfoPlistTask
      if ( !infoPlist && plistTask ) {
        infoPlist = plistTask.outputFile.path
        files[ "src/main/resources" ].add infoPlist
      }

      files.each { k, v ->
        def phase = k.contains( "resources" ) ? BuildPhase.Resources : BuildPhase.Sources
        xproj.addFilesToTarget name, k, phase, v
      }


      if ( infoPlist ) {
        xproj.buildSetting( name, buildConfiguration, "INFOPLIST_FILE", infoPlist )
      }
    }
  }
}

