package com.vperi.groovy.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * IdentifierGenerator
 * <p/>
 * Copyright © 2015 venkat
 * <p/>
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "unused" )
public final class IdentifierGenerator {
  private SecureRandom random = new SecureRandom();

  public String next() {
    return new BigInteger( 130, random ).toString( 32 );
  }
}
