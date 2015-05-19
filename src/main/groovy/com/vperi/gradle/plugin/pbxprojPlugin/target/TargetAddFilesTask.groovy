package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.tasks.XcodeProjTaskBase
import com.vperi.groovy.utils.StringExtension
import com.vperi.xcodeproj.BuildPhase
import groovy.util.logging.Slf4j
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.internal.file.DefaultSourceDirectorySet
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
@Slf4j
class TargetAddFilesTask extends XcodeProjTaskBase<TargetExt> {

  @Lazy def rootPath = project.rootDir.toPath()
  final def infoPlistFileName = "info.plist"
  String infoPlistFile = null

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void exec() {
    ext.with {
      Map<String, List<String>> files = [ : ]
      def n = ext.name.capitalize()

      DefaultSourceDirectorySet
      //      def conv = project.convention.getPlugin JavaPluginConvention
      [ "main", n ].each { configName ->
        def config = project.sourceSets."$configName"
        [ "objc", "swift", "resources" ].each {
          files.putAll getFileList( config."$it" )
        }
      }

      files.each { k, v ->
        def phase = k.contains( "resources" ) ? BuildPhase.Resources : BuildPhase.Sources
        xproj.addFilesToTarget name, k, phase, v
      }

      if ( infoPlistFile ) {
        log.info "target: $ext.name, Info.plist: $infoPlistFile"
        xproj.buildSetting( name, buildConfiguration, "INFOPLIST_FILE", infoPlistFile )
      }
    }
  }

  def getFileList( FileTree set ) {
    Map<String, List<String>> files = [ : ]
    def n = ext.name.capitalize()

    set.visit { FileVisitDetails f ->
      if ( f.isDirectory() ) return
      Path path = f.file.toPath()
      String filePath = f.file.path
      def relative = rootPath.relativize( path )
      group = StringExtension.removePrefix( relative.parent.toString(), "build/" )
      if ( !files.containsKey( group ) ) files[ group ] = [ ]
      files[ group ].add filePath
      if ( filePath.toLowerCase().contains( infoPlistFileName ) ) infoPlistFile = filePath
    }
    files
  }
}



