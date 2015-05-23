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
    def name = "${outputFile.path}.keychain"
    def keychain = new OsxKeychain( name, ext.password as String )
    keychain.create()
    keychain.unlock()

    if ( ext.certificates.size() ) {
      keychain.addCertificates ext.certificates
    }

    ext.imports.each {
      keychain.importItem it.key as String, it.value as String, null, null
    }
  }
}

