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
  Keychain setPassword( String password )

  /**
   * Returns the keychain password
   * @return
   */
  String getPassword()

  /**
   * Create keychain
   */
  Keychain create()

  /**
   *  Lock keychain
   */
  Keychain lock()

  /**
   * Unlock keychain
   */
  Keychain unlock()

  /**
   * Set settings for keychain
   * @param name -- name of setting
   *    lockOnSleep
   *    lockAfterTimeout (value, in seconds)
   * @param value
   */
  Keychain set( String name, Object value )

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
  Keychain addCertificates( List files )

  /**
   * Delete a certificate from a keychain specified by its common name
   * @param name
   * @param deleteUserTrustSettings
   */
  Keychain deleteCertificateByName( String name, boolean deleteUserTrustSettings )

  /**
   * Delete a certificate from a keychain specified by its SHA-1 hash
   * @param hash
   * @param deleteUserTrustSettings
   */
  Keychain deleteCertificateByHash( String hash, boolean deleteUserTrustSettings )

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
  Keychain importItem( String inputFile, String passphrase, String type, String format )

  //      boolean privateKeysAreWrapped, boolean privateKeysAreNonExtractable, boolean allowAnyApp, List<String> allowedApps, Map attribs )

  /**
   *  Find an identity (certificate + private key) satisfying a given policy.
   *  If no policy arguments are provided, the X.509 basic policy is assumed.
   * @param policy - Specify policy to evaluate.
   *    Supported policies: basic, ssl-client, ssl-server, smime, eap, ipsec,
   *    ichat, codesigning, sys-default, sys-kerberos-kdc
   * @param policySpecific - optional policy-specific string
   * @param validOnly - only valid identities
   * @return
   */
  String findIdentity( String policy, String policySpecific, boolean validOnly )
}

