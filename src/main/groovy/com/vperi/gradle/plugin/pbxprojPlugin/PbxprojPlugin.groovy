package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateExt
import com.vperi.gradle.plugin.pbxprojPlugin.cert.CertificateFactory
import com.vperi.gradle.extension.UppercasePropertyContainer
import com.vperi.gradle.plugin.pbxprojPlugin.extension.XCBuildConfiguration
import com.vperi.gradle.plugin.pbxprojPlugin.extension.XCBuildConfigurationFactory
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainExt
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.KeychainFactory
import com.vperi.gradle.tasks.RubyExecTask
import com.vperi.groovy.utils.ResourceUtils
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.internal.tasks.DefaultSourceSet
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.specs.Spec

/**
 * This is the main plugin file. Put a description of your plugin here.
 */
class PbxprojPlugin implements Plugin<Project> {
  Project project

  void apply( Project project ) {
    this.project = project

    project.with {
      apply plugin: "java-base"

      configureSourceSetDefaults( "objc", DefaultObjcSourceSet )
      configureSourceSetDefaults( "swift", DefaultSwiftSourceSet )

      [
          buildConfigurations: [ XCBuildConfiguration, new XCBuildConfigurationFactory( project: project ) ],
          keychains: [ KeychainExt, new KeychainFactory( project: project ) ],
          certificates: [ CertificateExt, new CertificateFactory( project: project ) ],
      ].each {
        extensions."$it.key" = container(
            it.value[ 0 ] as Class,
            it.value[ 1 ] as NamedDomainObjectFactory )
      }

      extensions.create( "pbxproj", UppercasePropertyContainer )
    }

    createTasks()
  }

  def createTasks() {

    project.with {

      task( "copyScripts" ) {
        def location = PbxprojPlugin.protectionDomain.codeSource.location.toURI().path
        if ( location.endsWith( ".jar" ) ) {
          ResourceUtils.copyJarFiles PbxprojPlugin, "/scripts", "$buildDir/scripts"
        } else {
          ResourceUtils.copyFiles file( "../../src/main/resources/scripts" ),
              file( "$buildDir/scripts" )
        }
      }

      task( "createPbxproj", type: RubyExecTask ) {
        workingDir "."
        script "build/scripts/createProj.rb"
        options p: "build/${project.name}.xcodeproj"
      }

      task( 'pbxproj', type: PbxprojTask ).dependsOn tasks[ "createPbxproj" ]

      tasks.findAll { it instanceof RubyExecTask }.each {
        it.dependsOn tasks[ "copyScripts" ]
      }
    }
  }

  def configureSourceSetDefaults( String name, Class klass ) {
    project.convention.getPlugin( JavaPluginConvention ).sourceSets.all { DefaultSourceSet sourceSet ->
      def ss = klass.newInstance( sourceSet.displayName, project.fileResolver )
      new DslObject( sourceSet ).convention.plugins.put name, ss

      def x = ss."$name"
      x.srcDir "src/$sourceSet.name/$name"
      sourceSet.resources.filter.exclude( { e -> x.contains e.file } as Spec )

      sourceSet.allSource.source x
    }
  }
}


