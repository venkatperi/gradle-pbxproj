package com.vperi.gradle.plugin.pbxprojPlugin.entitlements

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
class EntitlementsFactory extends ExtensionFactoryBase<EntitlementsExt> {
  Class klass = EntitlementsExt

  /**
   * Add tasks for this extension
   * @param x - the extension
   * @return
   */
  def afterEvaluate( EntitlementsExt x ) {
    def t = _ "${x.parent.prefix}CreateEntitlementsPlist", CreateEntitlementsPlistTask, x

    def addFilesTask = project.tasks.findByPath( "${x.parent.prefix}AddFiles" )
    if ( addFilesTask ) {
      addFilesTask.dependsOn t
    }
  }
}
