package com.vperi.groovy.utils

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
/**
 * Command.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@TupleConstructor
@Slf4j
public class Command {
  String executable
  File workingDir
  List env = null

  /**
   * Executes a shell command and returns the exit code and output
   *
   * Options is a map of the form [name:value] expands to --name value or -n value
   *  depending on the length of the name (single letter name == 1 dash)
   *
   * @param command -- the command
   * @param options -- options map
   * @param args -- trailing args
   * @return array , [exitCode, output]
   */
  public String exec( String command, Map options, String... args ) {
    exec command, options, args.toList()
  }

  /**
   *
   * @param command
   * @param options
   * @param args
   * @return
   */
  public String exec( String command, Map options, List args ) {
    def cmd = [ executable, command ]
    cmd.addAll options.collect { k, v -> "${dash k as String}$k $v" }
    cmd.addAll args
    def ( ret, output ) = Helper.exec( cmd.join( " " ), env, workingDir )
    if ( ret ) throw new RuntimeException( output as String )
    output
  }

  /**
   *
   * @param args
   * @return
   */
  public String exec( String... args ) {
    def cmd = [ executable ]
    cmd.addAll args
    def ( ret, output ) = Helper.exec( cmd.join( " " ), env, workingDir )
    if ( ret ) throw new RuntimeException( output as String )
    output
  }

  private static String dash( String name ) {
    name.length() <= 1 ? "-" : "--"
  }

  class Helper {

    static def exec( final String cmd, List<String> env, File workingDir ) throws IOException, InterruptedException {
      log.info "exec: $cmd\nworking dir: $workingDir"
      def stream = new ByteArrayOutputStream()
      def process = Runtime.runtime.exec( cmd, env as String[], workingDir )

      def stdout = null
      def stderr = null
      int retVal = 0
      try {
        int c
        stdout = process.inputStream
        stderr = process.errorStream

        while ( ( c = stdout.read() ) != -1 ) {
          stream.write( c )
        }
        while ( ( c = stderr.read() ) != -1 ) {
          stream.write( c )
        }
        retVal = process.waitFor()
      }
      finally {
        close( stdout, stderr )
      }
      [ retVal, stream.toString() ]
    }

    private static void close( final Closeable... closeables ) {
      closeables.each { Closeable c ->
        try {
          c.close()
        }
        catch ( Exception ignored ) {}
      }
    }
  }
}