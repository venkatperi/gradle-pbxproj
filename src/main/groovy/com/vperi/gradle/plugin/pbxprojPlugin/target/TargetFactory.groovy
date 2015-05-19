package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.extension.NamedObjectFactoryBase
import com.vperi.gradle.plugin.pbxprojPlugin.DefaultObjcSourceSet
import com.vperi.gradle.plugin.pbxprojPlugin.DefaultSwiftSourceSet
import com.vperi.gradle.plugin.pbxprojPlugin.PbxprojPlugin
import groovy.transform.Canonical
import org.gradle.api.plugins.JavaPluginConvention

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Canonical
class TargetFactory extends NamedObjectFactoryBase<TargetExt> {
  Class klass = TargetExt

  def addTasksFor( TargetExt x ) {
    project.with {
      def tName = x.name.capitalize()

      def createTask = _ "targetCreate$tName", CreateTargetTask, x
      def addFilesTask = _ "target${tName}AddFiles", TargetAddFilesTask, x

      addFilesTask.dependsOn createTask
      tasks[ "targets" ].dependsOn addFilesTask

      configureSourceSets tName
    }
  }

  def configureSourceSets( String name ) {
    def conv = project.convention.getPlugin( JavaPluginConvention )
    def ss = conv.sourceSets.create name
    PbxprojPlugin.configureSourceSetDefaults project, ss, "objc", DefaultObjcSourceSet
    PbxprojPlugin.configureSourceSetDefaults project, ss, "swift", DefaultSwiftSourceSet

    ss.resources.srcDirs( [ "src", "build/gen" ].collect { prefix ->
      "$prefix/$name/resources"
    }.toArray() )
  }
}


