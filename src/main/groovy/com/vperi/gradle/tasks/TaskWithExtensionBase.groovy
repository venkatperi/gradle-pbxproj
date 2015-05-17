package com.vperi.gradle.tasks

import com.vperi.gradle.extension.ExtensionBase
import org.gradle.api.internal.AbstractTask

/**
 * TaskWithExtensionBase.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class TaskWithExtensionBase<T extends ExtensionBase> extends AbstractTask {
  @Lazy File baseDir = project.file( "build/$ext.baseName" )
  T ext

  TaskWithExtensionBase() {
    super()
  }

  def propertyMissing( String name ) {
    ext."$name"
  }
}
