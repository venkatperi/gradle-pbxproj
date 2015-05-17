package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateExt
import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateFactory
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainExt
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainFactory
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetExt
import com.vperi.gradle.plugin.pbxprojPlugin.target.TargetFactory
import com.vperi.gradle.tasks.CreatePbxprojTask
import com.vperi.groovy.utils.ResourceUtils
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet

/**
 * This is the main plugin file. Put a description of your plugin here.
 */
class PbxprojPlugin implements Plugin<Project> {
  Project project

  void apply( Project project ) {
    this.project = project

    project.with {
      apply plugin: "java-base"

      configureSourceSets()
      configureSourceSetDefaults( "objc", DefaultObjcSourceSet )
      configureSourceSetDefaults( "swift", DefaultSwiftSourceSet )

      [
          targets: [ TargetExt, new TargetFactory( project: project ) ],
          keychains: [ KeychainExt, new KeychainFactory( project: project ) ],
          certificates: [ CertificateExt, new CertificateFactory( project: project ) ],
      ].each {
        extensions."$it.key" = container(
            it.value[ 0 ] as Class,
            it.value[ 1 ] as NamedDomainObjectFactory )
      }

      extensions.create( "pbxproj", PbxprojExt )
    }

    createTasks()
  }

  def createTasks() {

    def pwd = System.getProperty( "user.dir" )

    project.with {

      file("build").mkdirs()  //create build dir, just in case

      def copyScripts = task( "copyScripts" ) {
        def location = PbxprojPlugin.protectionDomain.codeSource.location.toURI().path
        if ( location.endsWith( ".jar" ) ) {
          ResourceUtils.copyJarFiles PbxprojPlugin, "/scripts", "$buildDir/scripts"
        } else {
          ResourceUtils.copyFiles new File( pwd, "src/main/resources/scripts" ),
              file( "$buildDir/scripts" )
        }
      }

      task( "createPbxproj", type: CreatePbxprojTask ).dependsOn copyScripts

      task( "targets" ).dependsOn tasks[ "createPbxproj" ]

      task( 'pbxproj' ).dependsOn tasks[ "targets" ]
    }
  }

  def getPluginConvention() {
    project.convention.getPlugin( JavaPluginConvention )
  }

  def configureSourceSets() {
    def main = pluginConvention.sourceSets.create( SourceSet.MAIN_SOURCE_SET_NAME );
  }

  def configureSourceSetDefaults( String name, Class klass ) {
    pluginConvention.sourceSets.all { DefaultSourceSet sourceSet ->
      def ss = klass.newInstance( sourceSet.displayName, project.fileResolver )
      new DslObject( sourceSet ).convention.plugins.put name, ss

      def x = ss."$name"
      x.srcDir "src/$sourceSet.name/$name"
      sourceSet."all${name.capitalize()}".source x
      sourceSet.allSource.source x
    }
  }
}


