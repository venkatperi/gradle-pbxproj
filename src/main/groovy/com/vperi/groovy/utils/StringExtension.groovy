package com.vperi.groovy.utils
/**
 * StringExtension.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
public class StringExtension {
  public static String removePrefix( String self, String prefix ) {
    self.startsWith( prefix ) ? self.substring( prefix.length() ) : self
  }
}
