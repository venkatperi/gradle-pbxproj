package com.vperi.gradle.extension

import com.vperi.gradle.tasks.TaskWithExtensionBase

/**
 * CreateInfoPlistTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
abstract class ResourceTaskBase<T extends ResourceExt> extends TaskWithExtensionBase<T> {
  @Lazy def outputDir = project.file( "build/gen/${ext.parent.name}/resources" )
}
