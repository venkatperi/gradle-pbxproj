package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.vperi.gradle.extension.PlistExt
import groovy.json.JsonSlurper
import groovy.transform.InheritConstructors
import groovy.transform.Memoized
import groovy.util.logging.Slf4j

/**
 * InfoPlistExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
@Slf4j
class InfoPlistExt extends PlistExt {

  @Override
  Map getDefaultProperties() {
    doGetDefaultProperties()
  }

  @Memoized
  Map doGetDefaultProperties() {
    def name = "${parent.platform.toString().toLowerCase()}_${parent.type.toString().toLowerCase()}"
    log.info( "template name: $name" )
    def slurper = new JsonSlurper()
    def input = this.class.getResourceAsStream( "/infoPlist/${name}.json" )
    def items = input ? slurper.parse( input ) as Map : [ : ]
    items
  }
}

