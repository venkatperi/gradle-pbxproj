package com.vperi.gradle.plugin.pbxprojPlugin.cert

import com.vperi.gradle.extension.ExtensionBase
import com.vperi.gradle.extension.ResourceExt
import groovy.transform.InheritConstructors
/**
 * Certificate.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@InheritConstructors
@SuppressWarnings( "GroovyUnusedDeclaration" )
class CertificateExt extends ResourceExt {
  String password
  boolean generateDN = true
  ExtensionBase distinguishedName
  int days = 365
  int bits = 1024

  def days( int days ) {
    this.days = days
  }

  def bits( int bits ) {
    this.bits = bits
  }

  def distinguishedName( Closure c ) {
    distinguishedName = new ExtensionBase()
    project.configure( distinguishedName, c )
    generateDN = false
  }

  def password( String p ) {
    this.password = p
  }

  def generateDN( boolean b ) {
    this.generateDN = b
  }
}
