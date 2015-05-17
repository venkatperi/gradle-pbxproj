package com.vperi.gradle.tasks

import com.vperi.gradle.extension.ExtensionBase
import com.vperi.xcodeproj.XcodeProj

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
  XcodeProj getXproj() {
    new XcodeProj( projectName: project.name,
        workingDir: project.file( "build" ),
        scriptDir: project.file( "build/scripts" ) )
  }

}

