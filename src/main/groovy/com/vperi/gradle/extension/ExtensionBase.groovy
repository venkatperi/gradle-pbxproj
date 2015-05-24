package com.vperi.gradle.extension

import com.vperi.groovy.utils.ObjectExt
import groovy.transform.Memoized
import groovy.util.logging.Slf4j
import org.gradle.api.Project

/**
 * ExtensionBase.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Slf4j(category = "ExtensionBase")
public class ExtensionBase {
  public final String name
  public final Project project

  final Map properties = [ : ]
  ContainerExtBase parent
  ExtensionBase logicalParent

  /**
   * Constructor
   * @param name -- name of this extension object
   * @param project -- our project
   */
  ExtensionBase( String name, Project project, def parent ) {
    def mc = new ExpandoMetaClass( this.class, false, true )
    mc.initialize()
    this.metaClass = mc
    this.name = name
    this.project = project
    this.parent = parent
    project.afterEvaluate {
      afterEvaluate()
    }
  }

  Map getMergedProperties() {
    def items = [ : ]
    items.putAll defaultProperties
    if ( logicalParent ) {
      items.putAll logicalParent.properties
    }
    items.putAll properties
    items
  }

  @SuppressWarnings( "GrMethodMayBeStatic" )
  Map getDefaultProperties() {
    [ : ]
  }

  @Memoized
  String getBasePath() {
    new File( baseDir, name ).path
  }

  @Memoized
  String getBaseName() {
    def name = this.class.simpleName.toLowerCase()
    def ext = [ "ext", "extension" ].find { name.endsWith it }
    def n = ext ? name.substring( 0, name.length() - ext.length() ) : name
    log.debug "baseName: $n"
    n
  }

  @Memoized
  def getBaseDir() {
    def d = project.file( "build/$baseName" )
    log.debug "baseDir: $d"
    d
  }

  String file( String ext ) {
    project.file( "${basePath}.$ext" ).path
  }

  def methodMissing( String name, args ) {
    log.debug "method missing: $name, $args, ${args.class}, ${properties[ name ]}"
    if ( ObjectExt.isCollectionOrArray( properties[ name ] ) ) {
      properties[ name ].addAll args
    } else if ( args.size() > 1 ) {
      properties.put name, args.toList()
    } else {
      properties.put name, args[ 0 ]
    }
  }

  def propertyMissing( String name ) {
    log.debug "propertyMissing: $name"
    mergedProperties.get name
  }

  def propertyMissing( String name, Object val ) {
    log.debug "propertyMissing: $name, $val"
    this.@properties.put( name, val )
  }

  def afterEvaluate() {
  }
}
