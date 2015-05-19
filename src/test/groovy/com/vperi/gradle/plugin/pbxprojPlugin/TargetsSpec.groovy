package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.xcodeproj.Language
import com.vperi.xcodeproj.Platform
import com.vperi.xcodeproj.TargetType
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
class TargetsSpec extends Specification {
  Project project
  def root = new File( "." )

  def setup() {
    project = ProjectBuilder.builder().build()
    project.with {
      apply plugin: "pbxproj"
    }
  }

  def "`targets` block add tasks"() {
    given:
    project.with {
      targets {
        Sample {}
      }
    }

    when:
    project.evaluate()

    then:
    project.extensions.targets.Sample
    project.tasks.findByPath( "targetCreateSample" )
    project.tasks.findByPath( "targets" )
  }

  def "set target params"() {
    given:
    project.with {
      targets {
        Sample {
          type "Application"
          language "Swift"
          platform "Ios"
          configuration "Debug"
        }
      }
    }

    when:
    project.evaluate()

    then:
    project.extensions.targets.Sample.type == TargetType.Application
    project.extensions.targets.Sample.language == Language.Swift
    project.extensions.targets.Sample.platform == Platform.Ios
    project.extensions.targets.Sample.configuration == "Debug"
  }

  def "`targets` dir sets"() {
    given:
    project.with {
      targets {
        Sample {
          type "Application"
          language "Swift"
          platform "Ios"
          configuration "Debug"
        }
      }
    }

    when:
    project.evaluate()

    then:
    project.extensions.targets.Sample
    project.tasks.findByPath( "targetCreateSample" )
    project.tasks.findByPath( "targets" )
  }
}
