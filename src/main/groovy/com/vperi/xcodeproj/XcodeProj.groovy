package com.vperi.xcodeproj

import com.vperi.groovy.utils.Command
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

/**
 * XcodeProj.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Canonical( includes = [ "projectName", "scriptDir", "workingDir" ] )
@Slf4j
class XcodeProj {
  def command = new Command( executable: "ruby" )
  String projectName
  File scriptDir

  def getWorkingDir() {
    command.workingDir
  }

  def setWorkingDir( File dir ) {
    command.workingDir = dir
  }

  def getProjectDir() {
    new File( workingDir, "${projectName}.xcodeproj" )
  }

  private def script( String name ) {
    new File( scriptDir, "${name}.rb" )
  }

  private def exec( String cmd, Map options, String... args ) {
    def s = script( "xcodeproj" ).path
    command.exec "$s $cmd", options, args.toList()
  }

  /**
   * Create a new project. Clobber any existing.
   * @return
   */
  def createProject() {
    exec "project create", [
        project: "${projectName}.xcodeproj",
        clobber: ""
    ]
  }

  /**
   * Creates a new target
   * @param name
   * @param type
   * @param platform
   * @param language
   * @param deploymentTarget
   * @return
   */
  def createTarget( String name, String type, String platform, String language,
      String deploymentTarget ) {
    def options = [
        project: "${projectName}.xcodeproj",
        name: name,
        type: type.toLowerCase(),
        platform: platform.toLowerCase(),
        language: language.toLowerCase() ]
    if ( deploymentTarget ) {
      options.deploymentTarget = deploymentTarget
    }

    exec "target create", options
  }

  /**
   * Create a new group
   * @param name of the group, in the form a/b/c. The group hierarchy
   *  will be created as needed under the main group
   * @param path - optional path
   */
  def createGroup( String name, String path ) {
    def options = [
        project: "${projectName}.xcodeproj",
        name: name
    ]
    if ( path ) {
      options.path = path
    }
    exec "group create", options
  }

  /**
   * Adds files to specified target
   * @param target
   * @param groupPath
   * @param files
   * @return
   */
  def addFilesToTarget( String target, String groupPath, BuildPhase phase, String... files ) {
    log.info "addFilesToTarget: $target, $groupPath, $phase, $files"
    exec "target add files", [
        project: "${projectName}.xcodeproj",
        target: target,
        path: groupPath,
        phase: phase.toString().toLowerCase(),
        files: files.join( "," ) ]
  }

  /**
   * Add files to target
   * @param target
   * @param groupPath
   * @param phase
   * @param files
   * @return
   */
  def addFilesToTarget( String target, String groupPath, BuildPhase phase, List<String> files ) {
    addFilesToTarget( target, groupPath, phase, files.toArray() as String[] )
  }

  /**
   * Gets or sets the named build setting. If target is not specified,
   *    the project level settings are modified
   * @param target - optional
   * @param configuration - the build configuration (Debug/Release)
   * @param name - build setting name
   * @param value - optional value. Returns current value if null
   * @return
   */
  def buildSetting( String target, String configuration, String name, String value = null ) {
    def options = [
        project: "${projectName}.xcodeproj",
        config: configuration,
        name: name,
    ]
    if ( value ) {
      options.value = value
    }
    if ( target ) {
      options.target = target
    }

    exec "build setting", options
  }

  /**
   *  Adds a file reference for one or more system framework to the project
   *  if needed and adds them to the Frameworks build phases.
   * @param target
   * @param frameworks
   * @return
   */
  def addSystemFrameworkToTarget( String target, List<String> frameworks ) {
    def options = [
        project: "${projectName}.xcodeproj",
        target: target,
        frameworks: frameworks.join( "," )
    ]

    exec "target add system frameworks", options
  }
}
