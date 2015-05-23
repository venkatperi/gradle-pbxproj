package com.vperi.xcodeproj

import com.vperi.groovy.utils.Command
import groovy.transform.Memoized

/**
 * XcodeBuild.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class XcodeBuild {
  def command = new Command( executable: "xcodebuild", singleDash: true )
  String projectName

  void setWorkingDir( File wd ) {
    command.workingDir = wd
  }

  File getWorkingDir() {
    command.workingDir
  }

  @Memoized
  String getVersion() {
    def version = command.exec( "-version" )
    def m = version =~ /(\d+\.?){3}/
    m[ 0 ][ 0 ]
  }

  static String getExists() {
    def res = new Command( executable: "which" ).exec( "xcodebuild" )
    res.contains "xcodebuild"
  }

  @Memoized
  Map getSdks() {
    def res = command.exec "-showsdks"
    def sdk1 = res.split( "\n" ).findAll { it.contains "-sdk" }.collect { it.split( "-sdk" )[ 1 ].trim() }
    def items = sdk1.collectEntries {
      def m = it =~ /^([a-zA-Z]+)(.*)$/
      [ ( m[ 0 ][ 1 ] ): m[ 0 ][ 2 ] ]
    }
    def sdks = [ : ]
    items.each { k, v ->
      if ( !sdks.containsKey( k ) ) {
        sdks[ k ] = [ ]
      }
      sdks[ k ].add v
    }
  }

  /**
   *
   * @param projectName
   * @param targetName
   * @return
   */
  def buildTarget( String targetName ) {
    command.exec project: projectName, target: targetName, "build"
  }
}