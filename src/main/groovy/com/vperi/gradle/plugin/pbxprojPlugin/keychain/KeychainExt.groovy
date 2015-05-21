package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.extension.ResourceExt
import com.vperi.groovy.utils.IdentifierGenerator
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
class KeychainExt extends ResourceExt {

  @Override
  Map getDefaultProperties() {
    [
        password: new IdentifierGenerator().next(),
        certificates: []
    ]
  }
}

