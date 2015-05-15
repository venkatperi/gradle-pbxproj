package com.vperi.gradle.tasks

import com.vperi.groovy.utils.Command
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

/**
 * RubyExecTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class RubyExecTask extends AbstractTask {
  String executable = "ruby"
  @Lazy Command command = new Command( executable: executable )
  String script
  Map options = [ : ]
  List args = [ ]

  @TaskAction
  def exec() {
    command.exec script, options, args
  }

  def script( String name ) {
    this.script = name
  }

  def options( Map options ) {
    this.options.putAll options
  }

  def args( Object... args ) {
    this.args.addAll args.toList()
  }

  def workingDir(File wd){
    command.workingDir = wd
  }
  def workingDir(String wd){
    command.workingDir = project.file wd
  }
}
