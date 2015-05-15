package com.vperi.gradle.plugin.pbxprojPlugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * MakeCertTaskSpec.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class CreateProjSpec extends Specification {
  Project project
  def root = new File( "build/test" )

  def setup() {
    root.mkdirs()
    project = ProjectBuilder.builder().withProjectDir( root ).build()
  }

  def "create xcode pbxproj"() {
    setup:
    project.with {
      apply plugin: "pbxproj"
    }
    project.evaluate()

    when:
    project.tasks[ "createPbxproj" ].execute()

    then:
    true
  }
}
