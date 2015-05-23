package com.vperi.gradle.plugin.pbxprojPlugin.project

import com.vperi.gradle.extension.ContainerExtBase
import com.vperi.gradle.plugin.pbxprojPlugin.entitlements.EntitlementsExt
import com.vperi.gradle.plugin.pbxprojPlugin.infoPlist.InfoPlistExt
import groovy.transform.InheritConstructors

/**
 * ProjectExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
public class ProjectExt extends ContainerExtBase {

  @Override
  String getOutputDir() {
    "main"
  }

  @Override
  String getPrefix() {
    "project${name.capitalize()}"
  }

  @Override
  EntitlementsExt createEntitlements() {
    new EntitlementsExt( "entitlements", project, null )
  }

  @Override
  InfoPlistExt createInfoPlist() {
    new InfoPlistExt( "info", project, null )
  }
}

