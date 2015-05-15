package com.vperi.gradle.plugin.pbxprojPlugin.keychain
import com.vperi.groovy.utils.Command
/**
 * OsxKeychain.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
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
    assert password, "Password can't be null"
    exec "create-keychain", [ p: password ], fileName
  }

  @Override
  void lock() {
    exec "lock-keychain", fileName
  }

  @Override
  void unlock() {
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
    exec "add-certificates", [ k: fileName ], files
  }

  @Override
  void deleteCertificateByName( String name, boolean deleteUserTrustSettings ) {
  }

  @Override
  void deleteCertificateByHash( String hash, boolean deleteUserTrustSettings ) {
  }

  private static String exec( String cmd, Map options, Object... args ) {
    def ( ret, output ) = Command.exec( "$security $cmd", options, args.toList() )
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

