package com.vperi.gradle.plugin.pbxprojPlugin
import com.vperi.gradle.extension.ExtensionBase
import groovy.transform.InheritConstructors
/**
 * PbxprojExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@InheritConstructors
class PbxprojExt extends ExtensionBase {

  @Override
  String getName() {
    this.@name ?: project.name
  }
}
