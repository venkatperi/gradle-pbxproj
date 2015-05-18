package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.dd.plist.NSDictionary
import com.dd.plist.PropertyListParser
import com.vperi.gradle.tasks.TaskWithExtensionBase
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
class CreateInfoPlistTask extends TaskWithExtensionBase<InfoPlistExt> {
  @Lazy def outputFile = project.file( "build/resources/Info.plist" )

  @Override
  def afterEvaluate() {
    outputs.file outputFile
  }

  @TaskAction
  void exec() {
    def root = new NSDictionary()
    ext.properties.each { k, v ->
      root.put( k as String, v as String )
    }
    outputFile.parentFile.mkdirs()
    PropertyListParser.saveAsXML( root, outputFile )
  }
}
