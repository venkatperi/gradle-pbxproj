package com.vperi.gradle.plugin.pbxprojPlugin.project

import com.vperi.gradle.extension.ExtensionFactoryBase
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
@Canonical
@Slf4j
class ProjectExtFactory extends ExtensionFactoryBase<ProjectExt> {
  Class klass = ProjectExt

  @Override
  def afterEvaluate( ProjectExt cert ) {
  }
}
