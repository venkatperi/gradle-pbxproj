package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.groovy.utils.Command
import com.vperi.groovy.utils.Throw
import groovy.transform.Canonical

/**
 * XcodeProj.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical( includes = [ "projectName", "scriptDir", "workingDir" ] )
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

  def script( String name ) {
    new File( scriptDir, "${name}.rb" )
  }

  def exec( String cmd, Map options, String... args ) {
    def s = script( "xcodeproj" ).path
    command.exec "$s $cmd", options, args
  }

  def create() {
    Throw.if projectDir.exists(), "Project directory already exists at ${projectDir.path}"
    exec "create", [ p: "${projectName}.xcodeproj" ]
  }

  def createTarget(String name, String type, String platform, String deploymentTarget, String language) {
  }
}
