package com.vperi.gradle.extension

import com.vperi.groovy.utils.Command

/**
 * Gradle task - Create a self signed certificate
 */
trait HasCommand {
  String executable
  File workingDir
  List env = [ ]
  @Lazy Command command = new Command( executable: executable, workingDir: workingDir, env: env )

  def exec( String cmd, Map options, List args ) {
    command.exec cmd, options, args
  }

  def exec( String cmd, Map options ) {
    command.exec cmd, options
  }
}