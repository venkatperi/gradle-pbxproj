package com.vperi.gradle.plugin.pbxprojPlugin

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
    root.deleteDir(  )
    root.mkdirs()
  }

  def "create project only if it doesn't exists"() {
    setup:
    def proj = new XcodeProj( projectName: "testProj", workingDir: root, scriptDir: scriptDir )

    when:
    proj.create()

    then:
    noExceptionThrown()

    when:
    project.create()

    then:
    thrown(Exception)
  }


}
