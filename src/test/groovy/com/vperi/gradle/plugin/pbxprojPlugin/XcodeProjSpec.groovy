package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.xcodeproj.XcodeProj
import spock.lang.Specification

/**
 * XcodeProjSpec.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class XcodeProjSpec extends Specification {
  static def root = new File( "build/test" )
  def scriptDir = new File( "../../src/main/resources/scripts" )

  def setupSpec() {
    root.deleteDir()
    root.mkdirs()
  }

  def "create project only if it doesn't exists"() {
    setup:
    def proj = new XcodeProj( projectName: "testProj", workingDir: root, scriptDir: scriptDir )

    when:
    proj.createProject()

    then:
    noExceptionThrown()

    and:
    when:
    project.createProject()

    then:
    thrown( Exception )
  }

  def "get setting"() {
    given:
    def proj = new XcodeProj( projectName: "testProj", workingDir: root, scriptDir: scriptDir )

    when:
    def value = proj.buildSetting( null, "Debug", "CLANG_CXX_LIBRARY" )

    then:
    value.trim().replace( '"', "" ) == "libc++"
  }
}
