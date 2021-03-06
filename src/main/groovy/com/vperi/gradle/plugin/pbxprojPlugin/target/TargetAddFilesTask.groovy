package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.tasks.XcodeProjTaskBase
import com.vperi.groovy.utils.StringExtension
import com.vperi.xcodeproj.BuildPhase
import groovy.util.logging.Slf4j
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.tasks.TaskAction

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
  static final def infoPlistFileName = "info.plist"
  String infoPlistFile = null

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void exec() {
    Map<String, List<String>> files = [ : ]
    def targetName = ext.name

    [ "main", targetName ].each { configName ->
      def config = project.sourceSets."$configName"
      [ "objc", "swift", "resources" ].each {
        files.putAll getFileList( config."$it" )
      }
    }

    files.each { k, v ->
      def phase = k.contains( "resources" ) ? BuildPhase.Resources : BuildPhase.Sources
      xproj.addFilesToTarget ext.name, k, phase, v
    }

    assert infoPlistFile, "No Info.plist found in source sets"
    log.info "target: $ext.name, Info.plist: $infoPlistFile"
    ext.buildConfigurations.each {
      xproj.buildSetting( ext.name, it, "INFOPLIST_FILE", infoPlistFile )
    }
  }

  /**
   * Visit the files in this file tree and return the list of files
   * @param set
   * @return
   */
  def getFileList( FileTree set ) {
    Map<String, List<String>> files = [ : ]

    set.visit { FileVisitDetails f ->
      if ( !f.isDirectory() ) {
        def parent = rootPath.relativize( f.file.toPath() ).parent.toString()
        group = StringExtension.removePrefix( parent, "build/" )
        if ( !files.containsKey( group ) ) files[ group ] = [ ]

        def filePath = f.file.path
        files[ group ].add filePath
        if ( filePath.toLowerCase().contains( infoPlistFileName ) ) infoPlistFile = filePath
      }
    }
    files
  }
}



