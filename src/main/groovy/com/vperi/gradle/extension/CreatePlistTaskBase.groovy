package com.vperi.gradle.extension

import com.dd.plist.NSDictionary
import com.dd.plist.NSObject
import com.dd.plist.PropertyListParser
import com.vperi.groovy.utils.ObjectExt
import org.gradle.api.tasks.TaskAction

/**
 * CreateInfoPlistTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
abstract class CreatePlistTaskBase<T extends PlistExt> extends ResourceTaskBase<T> {
  @Lazy def outputFile = new File( outputDir, fileName )

  abstract String getFileName()

  @Override
  def afterEvaluate() {
    outputs.file outputFile
  }

  @TaskAction
  void exec() {
    writeOutputFile plistObject
  }

  NSObject getPlistObject() {
    def root = new NSDictionary()
    ext.mergedProperties.each { k, v ->
      def val = v as String
      if ( v instanceof Boolean ) {
        val = v as Boolean
      } else if ( ObjectExt.isCollectionOrArray( v ) ) {
        val = v.collect { it.toString() }
      }
      root.put( k as String, val )
    }
    root
  }

  def writeOutputFile( NSObject root ) {
    outputFile.parentFile.mkdirs()
    PropertyListParser.saveAsXML( root, outputFile )
  }
}
