package com.vperi.gradle.plugin.pbxprojPlugin.keychain

/**
 * Keychain.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
interface Keychain {

  /**
   * Returns the keychain file name with path
   * @return
   */
  String getFileName()

  /**
   *
   * @param fileName
   */
  void setFileName( String fileName )

  /**
   * Sets the keychain password
   * @param password
   */
  void setPassword( String password )

  /**
   * Returns the keychain password
   * @return
   */
  String getPassword()

  /**
   * Create keychain
   */
  void create()

  /**
   *  Lock keychain
   */
  void lock()

  /**
   * Unlock keychain
   */
  void unlock()

  /**
   * Set settings for keychain
   * @param name -- name of setting
   *    lockOnSleep
   *    lockAfterTimeout (value, in seconds)
   * @param value
   */
  void set( String name, Object value )

  /**
   * Get keychain settings
   * @param name -- setting to get
   * @return
   */
  Object get( String name )

  /**
   * Add certficates contained in the specified files to the default keychain.
   * The files must contain one DER encoded X509 certificate each.
   * @param files
   */
  void addCertificates( String... files )

  /**
   * Delete a certificate from a keychain specified by its common name
   * @param name
   * @param deleteUserTrustSettings
   */
  void deleteCertificateByName( String name, boolean deleteUserTrustSettings )

  /**
   * Delete a certificate from a keychain specified by its SHA-1 hash
   * @param hash
   * @param deleteUserTrustSettings
   */
  void deleteCertificateByHash( String hash, boolean deleteUserTrustSettings )
}

