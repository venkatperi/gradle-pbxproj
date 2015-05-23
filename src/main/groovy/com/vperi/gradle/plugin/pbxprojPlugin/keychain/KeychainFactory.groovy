package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.extension.ExtensionFactoryBase
import groovy.transform.Canonical

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class KeychainFactory extends ExtensionFactoryBase<KeychainExt> {
  Class klass = KeychainExt

  def afterEvaluate( KeychainExt ext ) {
    def t = _ "${ext.parent.prefix}CreateKeychain", CreateKeychainTask, ext
    getOrCreateTask( "targets" ).dependsOn t
  }
}

