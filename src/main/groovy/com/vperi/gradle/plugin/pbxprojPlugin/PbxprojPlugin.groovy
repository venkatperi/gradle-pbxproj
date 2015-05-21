package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateExt
import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateFactory
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainExt
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainFactory
import com.vperi.gradle.plugin.pbxprojPlugin.project.ProjectExtFactory
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetExt
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetFactory
import com.vperi.gradle.tasks.CreatePbxprojTask
import com.vperi.groovy.utils.ResourceUtils
import groovy.util.logging.Slf4j
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet

/**
 * This is the main plugin file. Put a description of your plugin here.
 */
@Slf4j
class PbxprojPlugin implements Plugin<Project> {
  Project project

  void apply( Project project ) {
    this.project = project

    project.apply plugin: "java-base"

    configureSourceSets()
    configureSourceSetDefaults( "objc", DefaultObjcSourceSet )
    configureSourceSetDefaults( "swift", DefaultSwiftSourceSet )
    createExtensions()
    createDomainObjects()
    createTasks()
  }

  def createDomainObjects() {
    def projExt = project.extensions.project

    project.with {
      [
          targets: [ TargetExt, new TargetFactory( project: project, parent: projExt ) ],
          keychains: [ KeychainExt, new KeychainFactory( project: project ) ],
          certificates: [ CertificateExt, new CertificateFactory( project: project ) ],
      ].each {
        extensions."$it.key" = container(
            it.value[ 0 ] as Class,
            it.value[ 1 ] as NamedDomainObjectFactory )
      }
    }
  }

  def createExtensions() {
    project.extensions.add( "project", new ProjectExtFactory( project: project ).create( "project" ) )
  }

  def createTasks() {
    project.with {
      task( "createPbxproj", type: CreatePbxprojTask ).dependsOn copyScriptsTask
      task( "targets" ).dependsOn tasks[ "createPbxproj" ]
      task( 'pbxproj' ).dependsOn tasks[ "targets" ]
    }
  }

  Task getCopyScriptsTask() {
    project.with {
      task( "copyScripts" ) {
        def location = PbxprojPlugin.protectionDomain.codeSource.location.toURI().path
        if ( location.endsWith( ".jar" ) ) {
          ResourceUtils.copyJarFiles PbxprojPlugin, "/scripts", "$buildDir/scripts"
        } else {
          def pwd = System.getProperty( "user.dir" )
          ResourceUtils.copyFiles new File( pwd, "src/main/resources/scripts" ),
              project.file( "$buildDir/scripts" )
        }
      }
    }
  }

  def getPluginConvention() {
    project.convention.getPlugin JavaPluginConvention
  }

  def configureSourceSets() {
    pluginConvention.sourceSets.create "main"
    pluginConvention.sourceSets.create "test"
  }

  def configureSourceSetDefaults( String name, Class klass ) {
    pluginConvention.sourceSets.all { DefaultSourceSet sourceSet ->
      configureSourceSetDefaults( project, sourceSet, name, klass )
    }
  }

  static def configureSourceSetDefaults( Project project, SourceSet sourceSet, String name, Class klass ) {
    def ss = klass.newInstance( sourceSet.displayName, project.fileResolver )
    new DslObject( sourceSet ).convention.plugins.put name, ss

    SourceDirectorySet set = ss."$name"
    set.srcDirs( [ "src", "build/gen" ].collect {
      "$it/$sourceSet.name/$name"
    }.toArray() )

    //    sourceSet."all${name.capitalize()}".source set
    sourceSet.allSource.source set

    //    sourceSet.resources.srcDirs( [ "src", "build/gen" ].collect {
    //      "$it/$sourceSet.name/resource"
    //    }.toArray() )
  }
}


