package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.tasks.TaskWithExtensionBase
import org.gradle.api.tasks.TaskAction

import java.nio.file.FileAlreadyExistsException

/**
 * CreateKeychainTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class CreateKeychainTask extends TaskWithExtensionBase<KeychainExt> {

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void exec() {
    if ( !overwrite && project.file( ext.fileName ).exists() ) {
      throw new FileAlreadyExistsException( ext.fileName )
    }

    def keychain = new OsxKeychain( ext.fileName, ext.password )
    keychain.create()
  }
}

