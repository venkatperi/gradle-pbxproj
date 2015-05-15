package com.vperi.gradle.plugin.pbxprojPlugin

import com.vperi.gradle.plugin.pbxprojPlugin.keychain.Keychain
import com.vperi.gradle.plugin.pbxprojPlugin.keychain.OsxKeychain
import spock.lang.Specification

/**
 * KeychainSpec.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class KeychainSpec extends Specification {
  String fileName
  String password
  Keychain keychain

  def setup() {
    fileName = "/tmp/keychain-${UUID.randomUUID().toString()}"
    password = UUID.randomUUID().toString()
    keychain = new OsxKeychain( fileName, password )
    keychain.create()
  }

  def cleanup() {
    new File( fileName ).delete()
  }

  def "create a keychain creates the file on disk"() {
    expect:
    new File( fileName ).exists()
  }

  def "unlock the keychain"() {
    when:
    keychain.unlock()

    then:
    notThrown( RuntimeException )
  }
}
