package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

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
class InfoPlistFactory extends ExtensionFactoryBase<InfoPlistExt> {
  Class klass = InfoPlistExt

  /**
   * Add tasks for this extension
   * @param x - the extension
   * @return
   */
  def afterEvaluate( InfoPlistExt x ) {
    if ( x.parent ) {
      def t = _ "${x.parent.prefix}CreateInfoPlist", CreateInfoPlistTask, x
      getOrCreateTask( x.parent.prefix ).dependsOn t
    }
  }
}

