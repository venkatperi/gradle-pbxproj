package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.vperi.gradle.extension.PropertyContainer
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetExt
import groovy.json.JsonSlurper
import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import org.gradle.api.Project

/**
 * InfoPlistExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Slf4j
class InfoPlistExt extends PropertyContainer {
  TargetExt target

  /**
   *
   * @param name
   * @param project
   * @param target -- the immediate parent of this object
   */
  InfoPlistExt( String name, Project project, TargetExt target ) {
    super( name, project )
    this.target = target
  }

  @Override
  def afterEvaluate() {
    defaults.findAll { !properties.containsKey( it.key ) }.each { k, v ->
      properties[ k ] = v
    }
  }

  @Memoized
  def getDefaults() {
    def name = "${target.platform.toString().toLowerCase()}_${target.type.toString().toLowerCase()}"
    log.info( "template name: $name" )
    def slurper = new JsonSlurper()
    def input = this.class.getResourceAsStream( "/infoPlist/${name}.json" )
    assert input
    slurper.parse input
  }
}

