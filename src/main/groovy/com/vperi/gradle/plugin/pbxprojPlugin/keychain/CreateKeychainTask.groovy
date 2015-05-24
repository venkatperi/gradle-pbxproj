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
  Keychain keychain
  @Lazy def outputFile = new File( outputDir, ext.name )

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void exec() {
    def name = "${outputFile.path}.keychain"
    keychain = new OsxKeychain( name, ext.password as String )
        .create()
        .unlock()
        .set( "lockAfterTimeout", 1000 )

    if ( ext.certificates.size() ) {
      keychain.addCertificates( ext.certificates )
    }

    ext.imports.each {
      keychain.importItem it.key as String, it.value as String, null, null
    }

    findCodesignIdentity()
  }

  def findCodesignIdentity() {
    def identity = keychain.findIdentity( "codesigning", null, true )
    def line = identity.split( "\n" ).first().trim()
    def m = line =~ /^\d+\)\s+(\w+)\s+(.*)$/
    m[ 0 ][ 2 ]
  }
}

