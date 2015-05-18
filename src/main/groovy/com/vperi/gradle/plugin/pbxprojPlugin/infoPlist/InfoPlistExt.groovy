package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.vperi.gradle.extension.PropertyContainer
import groovy.transform.InheritConstructors

/**
 * InfoPlistExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
class InfoPlistExt extends PropertyContainer {
  @Lazy Map defaults = [
      CFBundleName: "\$(PRODUCT_NAME)",
      CFBundleIdentifier: "${project.group}.\$(PRODUCT_NAME:rfc1034identifier)",
      CFBundleInfoDictionaryVersion: "6.0",
      CFBundleVersion: "1",
      CFBundleExecutable: "\$(EXECUTABLE_NAME)",
      NSPrincipalClass: "NSApplication",
      CFBundlePackageType: "APPL",
      CFBundleIconFile: "",
      CFBundleSignature: "????",
      NSMainNibFile: "MainMenu",
      LSMinimumSystemVersion: "\$(MACOSX_DEPLOYMENT_TARGET)",
      CFBundleDevelopmentRegion: "en",
      NSHumanReadableCopyright: "Copyright © 2015. All rights reserved.",
      CFBundleShortVersionString: "1.0"
  ]

  @Override
  def afterEvaluate() {
    defaults.findAll { !properties.containsKey( it.key ) }.each { k, v ->
      properties[ k ] = v
    }
  }
}

