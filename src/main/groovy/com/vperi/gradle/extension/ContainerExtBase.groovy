package com.vperi.gradle.extension

import com.vperi.gradle.plugin.pbxprojPlugin.entitlements.EntitlementsExt
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistExt
import groovy.transform.InheritConstructors

/**
 * ContainerExtBase.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
abstract class ContainerExtBase extends ExtensionBase {

  abstract String getPrefix()

  @Lazy InfoPlistExt infoPlist = createInfoPlist()

  @Lazy EntitlementsExt entitlements = createEntitlements()

  abstract InfoPlistExt createInfoPlist()

  abstract EntitlementsExt createEntitlements()

  def infoPlist( Closure c ) {
    project.configure infoPlist, c
  }

  def entitlements( Closure c ) {
    project.configure entitlements, c
  }
}
