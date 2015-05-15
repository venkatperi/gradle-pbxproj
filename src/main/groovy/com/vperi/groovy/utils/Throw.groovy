package com.vperi.groovy.utils

/**
 * Throw.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class Throw {

  static def "if"( boolean condition, String message ) {
    if ( condition ) throw new Exception( message )
  }
}
