package com.vperi.gradle.plugin.pbxprojPlugin.target

import com.vperi.gradle.extension.ContainerExtBase
import com.vperi.gradle.plugin.pbxprojPlugin.entitlements.EntitlementsExt
import com.vperi.gradle.plugin.pbxprojPlugin.entitlements.EntitlementsFactory
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistExt
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistFactory
import com.vperi.xcodeproj.Language
import com.vperi.xcodeproj.Platform
import com.vperi.xcodeproj.TargetType
import groovy.transform.InheritConstructors
import groovy.transform.ToString

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
@ToString( includePackage = false, includeNames = true )
class TargetExt extends ContainerExtBase {

  @Override
  String getPrefix() {
    "target${name.capitalize()}"
  }

  @Override
  Map getDefaultProperties() {
    [ type: TargetType.Application,
        platform: Platform.Osx,
        language: Language.Swift,
        deploymentTarget: "",
        buildConfiguration: "Debug",
        systemFrameworks: [ ]
    ]
  }

  @Override
  EntitlementsExt createEntitlements() {
    def x = new EntitlementsFactory(
        project: project, parent: this ).create( "${name}Entitlements" )
    x.logicalParent = parent.entitlements
    x
  }

  @Override
  InfoPlistExt createInfoPlist() {
    def x = new InfoPlistFactory(
        project: project, parent: this ).create( "${name}InfoPlist" )
    x.logicalParent = parent.infoPlist
    x
  }
}
