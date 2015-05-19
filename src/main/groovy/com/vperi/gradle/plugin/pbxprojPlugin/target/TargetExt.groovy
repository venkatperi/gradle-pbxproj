package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.extension.ExtensionBase
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistExt
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistFactory
import com.vperi.xcodeproj.Language
import com.vperi.xcodeproj.Platform
import com.vperi.xcodeproj.TargetType
import groovy.transform.InheritConstructors

/**
 * TargetExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
class TargetExt extends ExtensionBase {
  TargetType type
  Platform platform
  Language language
  String deploymentTarget
  String buildConfiguration
  @Lazy InfoPlistExt infoPlist = new InfoPlistFactory( project: project, target: this ).create( "${name}InfoPlist" )

  def infoPlist( Closure c ) {
    project.configure infoPlist, c
  }
}
