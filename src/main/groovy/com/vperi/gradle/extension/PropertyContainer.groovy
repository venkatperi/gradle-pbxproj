package com.vperi.gradle.extension

import groovy.transform.Canonical

/**
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class PropertyContainer {
  String name
  Map properties = [ : ]

  PropertyContainer() {
    def mc = new ExpandoMetaClass( PropertyContainer, false, true )
    mc.initialize()
    this.metaClass = mc
  }

  def methodMissing( String name, args ) {
    properties.put( name, args )
  }

  def propertyMissing( String name ) {
    properties.get( property )
  }
}

