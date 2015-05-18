package com.vperi.gradle.extension

import org.gradle.api.Project

/**
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class PropertyContainer extends ExtensionBase {
  Map properties = [ : ]

  PropertyContainer( String name, Project project ) {
    super( name, project )
    def mc = new ExpandoMetaClass( this.class, false, true )
    mc.initialize()
    this.metaClass = mc
  }

  def methodMissing( String name, args ) {
    this.@properties.put( name, args[ 0 ] )
  }

  def propertyMissing( String name ) {
    this.@properties.get( name )
  }

  def propertyMissing( String name, Object val ) {
    this.@properties.put( name, val )
  }
}

