package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.extension.ExtensionFactoryBase
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
class TargetFactory extends ExtensionFactoryBase<TargetExt> {
  Class klass = TargetExt

  def afterEvaluate( TargetExt x ) {
    def createTask = _ "${x.prefix}Create", CreateTargetTask, x
    def addFilesTask = _ "${x.prefix}AddFiles", TargetAddFilesTask, x

    addFilesTask.dependsOn createTask
    project.tasks[ "targets" ].dependsOn addFilesTask

    configureSourceSets x.name
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


