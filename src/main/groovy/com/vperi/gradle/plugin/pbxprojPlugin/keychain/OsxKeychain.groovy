package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.groovy.utils.Command
import com.vperi.groovy.utils.Throw
import groovy.util.logging.Slf4j

/**
 * OsxKeychain.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Slf4j
public class OsxKeychain implements Keychain {
  private static final String security = "/usr/bin/security"
  String fileName
  String password

  /**
   * Constructor
   * @param fileName
   * @param password
   */
  OsxKeychain( String fileName, String password ) {
    this.fileName = fileName
    this.password = password
  }

  void create() {
    log.info( "creating keychain: $fileName" )
    assert password, "Password can't be null"
    exec "createProject-keychain", [ p: password ], fileName
  }

  @Override
  void lock() {
    log.info( "lock keychain: $fileName" )
    exec "lock-keychain", fileName
  }

  @Override
  void unlock() {
    log.info( "unlock keychain: $fileName" )
    exec "unlock-keychain", [ p: password ], fileName
  }

  @Override
  void set( String name, Object value ) {
    def options = [ : ]
    switch ( name ) {
      case 'lockOnSleep':
        options.lockOnSleep = ""
        break
      case 'lockAfterTimeout':
        options.lockAfterTimeout = ""
        options.t = value
        break
      default:
        throw new IllegalArgumentException( "unknown property: $name" )
    }

    log.info( "set props: $fileName, $name, $value" )
    exec "set-keychain-settings", options, fileName
  }

  void setPassword( String password ) {
    if ( this.password ) {
      //don't set on first init
      exec "set-keychain-password", [ o: this.password, p: password ], fileName
      this.password = password
    }
  }

  @Override
  Object get( String name ) {
    return null
  }

  @Override
  void addCertificates( String... files ) {
    log.info( "add certificates: $fileName, $files" )
    exec "add-certificates", [ k: fileName ], files
  }

  @Override
  void deleteCertificateByName( String name, boolean deleteUserTrustSettings ) {
    Throw.notImplemented "deleteCertificateByName"
  }

  @Override
  void deleteCertificateByHash( String hash, boolean deleteUserTrustSettings ) {
    Throw.notImplemented "deleteCertificateByHash"
  }

  private static String exec( String cmd, Map options, List<String> args ) {
    def ( ret, output ) = Command.exec( "$security $cmd", options, args )
    if ( ret ) throw new RuntimeException( output as String )
    output
  }

  private static String exec( String... args ) {
    def cmd = [ security ]
    cmd.addAll args
    def ( ret, output ) = Command.exec( cmd.join( " " ) )
    if ( ret ) throw new RuntimeException( output as String )
  }
}

