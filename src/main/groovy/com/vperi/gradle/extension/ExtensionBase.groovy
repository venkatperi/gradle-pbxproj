package com.vperi.gradle.extension

import groovy.transform.Canonical
import org.gradle.api.Project

/**
 * ExtensionBase.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class ExtensionBase {
  @Lazy File baseDir = project.file( "build/$baseName" )
  @Lazy String basePath = project.file( "$baseDir.path/$name" ).path
  String name
  Project project

  String file( String ext ) {
    project.file( "${basePath}.$ext" ).path
  }

  String getBaseName() {
    def name = this.class.simpleName.toLowerCase()
    def ext = [ "ext", "extension" ].find { name.endsWith it }
    ext ? name.substring( 0, name.length() - ext.length() ) : name
  }

  def methodMissing( String name, args ) {
    if ( name in [ "name", "project" ] ) return
    if ( this.properties.containsKey( name ) ) {
      this."$name" = args[0]
      return
    }
    throw new MissingMethodException( name, null )
  }
}
