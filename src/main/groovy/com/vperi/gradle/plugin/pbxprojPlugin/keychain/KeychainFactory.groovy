package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.extension.NamedObjectFactoryBase
import groovy.transform.Canonical

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class KeychainFactory extends NamedObjectFactoryBase<KeychainExt> {
  Class klass = KeychainExt

  def addTasksFor( KeychainExt ext ) {
    project.task( "keychainCreate${ext.name.capitalize()}", type: CreateKeychainTask ) {
      it.ext = ext
    }
  }
}

