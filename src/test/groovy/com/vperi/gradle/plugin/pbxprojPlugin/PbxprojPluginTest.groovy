package com.vperi.gradle.plugin.pbxprojPlugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class PbxprojPluginTest extends Specification {
  def root
  Project project

  def setup() {
    root = new File( ".", "src/test/resources" )
    project = ProjectBuilder.builder().withProjectDir( root ).build()
  }

  def "apply the plugin"() {
    given:
    project.with {
      apply plugin: 'pbxproj'
    }

    when:
    project.evaluate()

    then:
    project.sourceSets.main != null
    project.sourceSets.main.objc != null
    project.sourceSets.main.swift != null
  }

  def "exec tasks"() {
    given:
    project.with {
      apply plugin: 'pbxproj'

      targets {
        Sample {
          type "Application"
          language "Swift"
          platform "Osx"
          configuration "Debug"
        }
      }
    }

    when:
    project.evaluate()
    project.tasks[ "createPbxproj" ].execute()
//    project.tasks[ "targetCreateSample" ].execute()
    project.tasks[ "targetSampleAddFiles" ].execute()

    then:
    true
  }
}