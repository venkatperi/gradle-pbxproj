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
class MakeCertTaskSpec extends Specification {
  Project project
  def root = new File( "." )

  def setup() {
    project = ProjectBuilder.builder().withProjectDir( root ).build()
  }

  def "certificates add tasks"() {
    setup:
    project.with {
      apply plugin: "pbxproj"

      certificates {
        abcd {
        }
      }
    }

    when:
    project.evaluate()

    then:
    project.tasks[ "certCreateAbcd" ]
  }

  def "execute certificate task"() {
    setup:
    project.with {
      apply plugin: "pbxproj"

      certificates {
        abcd {
          password "asdfasdfasd"
        }
      }
    }
    project.evaluate()
    def task = project.tasks[ "certCreateAbcd" ]

    when:
    task.execute()

    then:
    project.file( "${task.basePath}.config" ).exists()
    project.file( "${task.basePath}.crt" ).exists()
    project.file( "${task.basePath}.csr" ).exists()
    project.file( "${task.basePath}.der" ).exists()
    project.file( "${task.basePath}.key" ).exists()
  }
}
