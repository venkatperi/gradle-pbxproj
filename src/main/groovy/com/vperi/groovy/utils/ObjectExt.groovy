package com.vperi.groovy.utils

/**
 * ObjectExt.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
public class ObjectExt {
  public static boolean isCollectionOrArray( object ) {
    [ Collection, Object[ ] ].any { it.isAssignableFrom( object.getClass() ) }
  }

  public static boolean isMap( object ) {
    [ Map ].any { it.isAssignableFrom( object.getClass() ) }
  }
}
