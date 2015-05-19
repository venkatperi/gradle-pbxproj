package com.vperi.gradle.extension

import org.gradle.api.Project

/**
 * ExtensionBase.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class ExtensionBase {
  String name
  Project project
  @Lazy protected File baseDir = project.file( "build/$baseName" )
  @Lazy protected String basePath = project.file( "$baseDir.path/$name" ).path

  /**
   * Constructor
   * @param name -- name of this extension object
   * @param project -- our project
   */
  ExtensionBase( String name, Project project ) {
    this.name = name
    this.project = project
    project.afterEvaluate {
      afterEvaluate()
    }
  }

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
      this."$name" = args[ 0 ]
      return
    }
    throw new MissingMethodException( name, null )
  }

  def afterEvaluate() {
  }
}
