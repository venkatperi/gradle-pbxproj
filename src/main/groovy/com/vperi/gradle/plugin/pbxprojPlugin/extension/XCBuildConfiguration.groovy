package com.vperi.gradle.plugin.pbxprojPlugin.extension

import com.vperi.gradle.extension.PropertyContainer
import groovy.transform.Canonical
import org.gradle.api.Project

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class XCBuildConfiguration {
  String name
  Project project
  PropertyContainer buildSettings

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  def buildSettings( Closure c ) {
    buildSettings = new PropertyContainer()
    project.configure( buildSettings, c )
  }

}


