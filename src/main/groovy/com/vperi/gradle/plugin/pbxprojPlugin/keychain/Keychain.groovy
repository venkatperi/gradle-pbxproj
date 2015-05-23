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

  /**
   * Imports one or more items from `inputFile` into this keychain.
   * @param inputFile
   * @param passphrase - the unwrapping passphrase
   * @param type Specify the type of items to import. Possible types are cert,
   *    pub, priv, session, cert, and agg. Pub, priv, and session refer to keys; agg
   *    is one of the aggregate types (pkcs12 and PEM sequence). The command can
   *    often figure out what item_type an item contains based in the filename
   *    and/or item_format.
   * @param format - Possible formats are openssl, bsafe, raw, pkcs7, pkcs8,
   *    pkcs12, x509, openssh1, openssh2, and pemseq. The command can often figure
   *    out what format an item is in based in the filename and/or item_type.
   */
  void importItem( String inputFile, String passphrase, String type, String format)
//      boolean privateKeysAreWrapped, boolean privateKeysAreNonExtractable, boolean allowAnyApp, List<String> allowedApps, Map attribs )
}

