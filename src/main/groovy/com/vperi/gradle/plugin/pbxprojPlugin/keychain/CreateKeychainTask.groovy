package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.gradle.extension.ResourceTaskBase
import groovy.util.logging.Slf4j
import org.gradle.api.tasks.TaskAction

/**
 * CreateKeychainTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Slf4j
class CreateKeychainTask extends ResourceTaskBase<KeychainExt> {
  @Lazy def outputFile = new File( outputDir, ext.name )

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void exec() {
    log.info "Creating keychain file: ${outputFile.path}"
    def keychain = new OsxKeychain( outputFile.path, ext.password as String )
    keychain.create()

    //noinspection GroovyAssignabilityCheck
    keychain.addCertificates ext.certificates.toArray()
  }
}

