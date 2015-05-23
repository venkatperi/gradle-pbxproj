package com.vperi.gradle.plugin.pbxprojPlugin.keychain

import com.vperi.groovy.utils.Command
import com.vperi.groovy.utils.Throw
import groovy.util.logging.Slf4j
import org.apache.tools.ant.util.StringUtils

/**
 * OsxKeychain.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Slf4j( category = "OsxKeychain" )
public class OsxKeychain implements Keychain {
  def command = new Command( executable: "/usr/bin/security" )
  String fileName
  String password

  /**
   * Constructor
   * @param fileName
   * @param password
   */
  OsxKeychain( String fileName, String password ) {
    this.fileName = strip fileName
    this.password = password
  }

  void create() {
    log.info( "create keychain: $fileName" )
    assert password, "Password can't be null"
    exec "create-keychain", [ p: password ], fileName
  }

  @Override
  void lock() {
    log.info( "lock keychain: $fileName" )
    exec "lock-keychain", [ : ], fileName
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
    def items = files.collect { strip it }
    log.info( "add certificates: $fileName, $items" )
    exec "add-certificates", [ k: fileName ], items
  }

  @Override
  void deleteCertificateByName( String name, boolean deleteUserTrustSettings ) {
    Throw.notImplemented "deleteCertificateByName"
  }

  @Override
  void deleteCertificateByHash( String hash, boolean deleteUserTrustSettings ) {
    Throw.notImplemented "deleteCertificateByHash"
  }

  @Override
  void importItem( String inputFile, String passphrase, String type, String format ) {
    def options = [ P: passphrase ]
    if ( type ) {
      options.t = type
    }
    if ( format ) {
      options.f = format
    }

    def f = strip inputFile
    exec "import $f", options
  }

  private static String strip(String str){
    StringUtils.removePrefix( str, "file://" )
  }

  private String exec( String cmd, Map options, String... args ) {
    command.exec cmd, options, args.toList()
  }

  private String exec( String cmd, Map options, List args ) {
    command.exec cmd, options, args
  }

  //  private static String exec( String... args ) {
  //    def cmd = [ security ]
  //    cmd.addAll args
  //    Command.exec( cmd.join( " " ) )
  //  }
}

