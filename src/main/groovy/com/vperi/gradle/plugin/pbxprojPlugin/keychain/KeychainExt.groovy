package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.extension.ExtensionBase
import groovy.transform.InheritConstructors

/**
 * Certificate.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@InheritConstructors
@SuppressWarnings( "GroovyUnusedDeclaration" )
class KeychainExt extends ExtensionBase {
  String password
  boolean overwrite = false

  def getFileName() {
    file( "keychain" )
  }

  def password( String p ) {
    this.password = p
  }

  def overwrite( boolean b ) {
    this.overwrite = b
  }
}

