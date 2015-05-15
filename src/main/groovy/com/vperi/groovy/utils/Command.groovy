package com.vperi.groovy.utils

import groovy.transform.TupleConstructor

/**
 * Command.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@TupleConstructor
public class Command {
  String executable
  File workingDir
  List env = null

  public String exec( String command, Map options, String... args ) {
   exec command, options, args.toList()
  }

  public String exec( String command, Map options, List args ) {
    def cmd = [ executable, command ]
    cmd.addAll options.collect { k, v -> "-$k $v" }
    cmd.addAll args
    def ( ret, output ) = Helper.exec( cmd.join( " " ), env, workingDir )
    if ( ret ) throw new RuntimeException( output as String )
    output
  }

  public String exec( String... args ) {
    def cmd = [ executable ]
    cmd.addAll args
    def ( ret, output ) = Helper.exec( cmd.join( " " ), env, workingDir )
    if ( ret ) throw new RuntimeException( output as String )
    output
  }

  class Helper {

    static def exec( final String cmd, List<String> env, File workingDir ) throws IOException, InterruptedException {
      def bout = new ByteArrayOutputStream()
      def process = Runtime.runtime.exec( cmd, env as String[], workingDir )

      def inp = null
      def err = null
      def out = null
      int retVal = 0
      try {
        int c
        inp = process.inputStream
        err = process.errorStream
        out = process.outputStream

        while ( ( c = inp.read() ) != -1 ) {
          bout.write( c )
        }
        while ( ( c = err.read() ) != -1 ) {
          bout.write( c )
        }
        retVal = process.waitFor()
      }
      finally {
        close( inp, out, err )
      }
      [ retVal, bout.toString() ]
    }

    private static void close( final Closeable... closeables ) {
      closeables.each { c ->
        try {
          c.close()
        }
        catch ( Exception ignored ) {}
      }
    }
  }
}