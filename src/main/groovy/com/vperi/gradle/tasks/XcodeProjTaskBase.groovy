package com.vperi.gradle.tasks

import com.vperi.gradle.extension.ExtensionBase
import com.vperi.xcodeproj.XcodeBuild
import com.vperi.xcodeproj.XcodeProj
import groovy.transform.Memoized

/**
 * CreateTargetTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
class XcodeProjTaskBase<T extends ExtensionBase> extends TaskWithExtensionBase<T> {
  @Lazy def workingDir = project.file( "build" )

  @Memoized
  XcodeProj getXproj() {
    new XcodeProj( projectName: project.name,
        workingDir: workingDir,
        scriptDir: project.file( "build/scripts" ) )
  }

  @Memoized
  XcodeBuild getBuilder() {
    new XcodeBuild( workingDir: workingDir,
        projectName: "${project.name}.xcodeproj" )
  }
}

